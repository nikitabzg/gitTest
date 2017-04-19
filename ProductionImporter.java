package be.ulb.imdb.ba2.dataImport.reader.production;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import be.ulb.imdb.ba2.dataImport.db.ProductionDataAccessor;
import be.ulb.imdb.ba2.dataImport.model.Episode;
import be.ulb.imdb.ba2.dataImport.model.Movie;
import be.ulb.imdb.ba2.dataImport.model.Production;
import be.ulb.imdb.ba2.dataImport.model.Serie;
import be.ulb.imdb.ba2.dataImport.reader.FileReader;
import be.ulb.imdb.ba2.dataImport.reader.ProductionKey;
import be.ulb.imdb.ba2.dataImport.reader.ProductionLine;

public class ProductionImporter {

	private ProductionParser productionParser = new ProductionParser();
	private ProductionDataAccessor productionDataAccessor = new ProductionDataAccessor();
	public static final String suspendedCode = "{{SUSPENDED}}";
	public static final int max_buffered_size = 1000;

	public void importProductions(String filePath) {

		List<Movie> movies = new ArrayList<>();
		List<Serie> series = new ArrayList<>();
		List<Episode> episodes = new ArrayList<>();
		FileReader reader = new FileReader(filePath);
		BufferedReader br = reader.readFile();
		String line = "";
		int i = 0;
		while (line != null) {
			try {
				line = br.readLine();
				i++;
				if (line != null && !line.contains(suspendedCode)) {
					ProductionLine productionLine = productionParser.parse(line);
					if (line != null) {
						ProductionKey key = ProductionParser.parseKey(productionLine.getKey());
						if (ProductionParser.checkProductionYear(key.getYear())) {
							Production prod = transformProductionLine(productionLine, key);
							if (prod instanceof Episode) {
								if (ProductionParser.checkProductionYear(productionLine.getEpisodeYear())) {
									((Episode) prod).setSerieID(ProductionParser.exctractParentKey(prod.getId()));
									episodes.add((Episode) prod);
									if (episodes.size() == max_buffered_size) {
										//System.out.println(episodes.get(0));
										//System.out.println(episodes.get(1));
										productionDataAccessor.saveEpisodes(episodes);
										episodes.clear();
									}
								}
							} else if (prod instanceof Serie) {
								series.add((Serie) prod);
								if (series.size() == max_buffered_size) {
									productionDataAccessor.saveSeries(series);
									series.clear();
								}
							} else {
								movies.add((Movie) prod);
								if (movies.size() == max_buffered_size) {
									productionDataAccessor.saveMovies(movies);
									movies.clear();
								}
							}
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("error reading line: " + line);
				e.printStackTrace();
				throw new RuntimeException(e);
			} finally {

			}

		}
		try {
			if (!episodes.isEmpty()) {
				System.out.println("save more episodes");
				productionDataAccessor.saveEpisodes(episodes);
				episodes.clear();
			}
			if (!series.isEmpty()) {
				System.out.println("saving more series");
				productionDataAccessor.saveSeries(series);
			}
			if (!movies.isEmpty()) {
				productionDataAccessor.saveMovies(movies);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		try {
			br.close();
		} catch (Exception e) {

		}
	}

	private Production transformProductionLine(ProductionLine p, ProductionKey k) {
		Production result = null;
		char type = p.getType();
		if (type == ProductionLine.TYPE_EPISODE) {
			result = new Episode();
			result.setId(p.getKey());
			((Episode) result).setEpisodeNumber(k.getEpisodeNumber());
			((Episode) result).setSeasonNumber(k.getSeasonNumber());
			((Episode) result).setSerieID(ProductionParser.exctractParentKey(result.getId()));
			result.setTitle(k.getEpisodeTitle());
			result.setYear(p.getEpisodeYear());
		} else if (type == ProductionLine.TYPE_SERIE) {
			result = new Serie();
			result.setId(p.getKey());
			((Serie) result).setEndYear(p.getEndYear());
			result.setTitle(k.getTitle());
			result.setYear(k.getYear());
		} else {
			result = new Movie();
			result.setId(p.getKey());
			result.setTitle(k.getTitle());
			result.setYear(k.getYear());
		}
		return result;
	}
}
