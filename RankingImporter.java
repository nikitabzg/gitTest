package be.ulb.imdb.ba2.dataImport.reader.ranking;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import be.ulb.imdb.ba2.dataImport.db.ProductionDataAccessor;
import be.ulb.imdb.ba2.dataImport.reader.FileReader;
import be.ulb.imdb.ba2.dataImport.reader.ProductionKey;
import be.ulb.imdb.ba2.dataImport.reader.production.ProductionImporter;
import be.ulb.imdb.ba2.dataImport.reader.production.ProductionParser;

public class RankingImporter {

	private RankingParser rankingParser = new RankingParser();
	private ProductionDataAccessor productionDataAccessor = new ProductionDataAccessor();

	public void importNotes(String filePath) {
		List<RankingLine> notes = new ArrayList<>();
		FileReader reader = new FileReader(filePath);
		BufferedReader br = reader.readFile();
		String line = "";
		int i = 0;
		while (line != null) {
			try {
				line = br.readLine();
				i++;
				if (line != null && !line.isEmpty()
						&& !line.contains(ProductionImporter.suspendedCode)) {
					RankingLine rankingLine = rankingParser.parse(line);
					if (rankingLine != null){
						ProductionKey pk = ProductionParser.parseKey(rankingLine.getKey());
						if (ProductionParser.checkProductionYear(pk.getYear())){
							notes.add(rankingLine);
							if (notes.size() == ProductionImporter.max_buffered_size){
								productionDataAccessor.saveNotes(notes);
								System.out.println("saveNotes called from importer");
								notes.clear();
							}
						}
					}
				}
			} catch (Exception e) {
				System.out.println("Error reading line: " + line);
				e.printStackTrace();
				throw new RuntimeException(e);
			}

		}
		try {
			br.close();
		} catch (Exception e2) {
		}

	}

	public static void main(String[] args) {
		new RankingImporter().importNotes("notesFull.txt");
	}
}
