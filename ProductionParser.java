package be.ulb.imdb.ba2.dataImport.reader.production;

import be.ulb.imdb.ba2.dataImport.reader.InvalidLineException;
import be.ulb.imdb.ba2.dataImport.reader.ProductionKey;
import be.ulb.imdb.ba2.dataImport.reader.ProductionLine;

public class ProductionParser {

	public ProductionLine parse(String line) {
		ProductionLine productionLine = new ProductionLine();
		String key = extractKey(line, 0);
		productionLine.setKey(key);
		char type = productionLine.getType();
		int endKeyIdx = key.length();
		if (type == ProductionLine.TYPE_SERIE) {
			int idxYyearSep = line.indexOf("-", endKeyIdx);
			if (idxYyearSep > 0 && idxYyearSep < line.length() - 1) {
				String sEndYear = line.substring(idxYyearSep + 1).trim();
				if (!sEndYear.contains("?"))
					productionLine.setEndYear(Integer.valueOf(sEndYear));
			}
		} else if (type == ProductionLine.TYPE_EPISODE
				&& endKeyIdx < line.length() - 1) {
			String sYear = line.substring(endKeyIdx).trim();
			if (!sYear.isEmpty() && !sYear.contains("?"))
				try {
					Integer year = Integer.valueOf(sYear);
					productionLine.setEpisodeYear(year);
				} catch (Exception e) {
					System.out.println("Invalid episode date: " + line);
				}
		}
		return productionLine;
	}

	public static String extractKey(String line, int startIdx) {
		int idx = line.indexOf("\t", startIdx);
		if (idx == -1 || idx <= startIdx) {
			idx = line.lastIndexOf("}") + 1;
			if (idx == 0 || idx <= startIdx)
				idx = line.lastIndexOf(")") + 1;
		}
		if (idx > 0 && idx > startIdx) {
			if (idx == line.length())
				return line.substring(startIdx).trim();
			else
				return line.substring(startIdx, idx + 1).trim();
		}

		throw new InvalidLineException(line);
	}
	
	public static String exctractParentKey(String episodeKey){
		int idx = episodeKey.indexOf('{');
		if (idx == -1){
			throw new InvalidLineException(episodeKey);
		}
		String serieKey = episodeKey.substring(0, idx).trim();
		return serieKey;
	}

	public static ProductionKey parseKey(String key) {
		if (key == null)
			return null;
		ProductionKey productionKey = new ProductionKey();
		int idxEndYearDiff = fillTitleAndYearInProductionkey(key, productionKey);
		fillEpisodeNameSeasonNumberInProductionKey(productionKey, key, idxEndYearDiff);
		return productionKey;
	}

	private static int fillTitleAndYearInProductionkey(String key,
			ProductionKey productionKey) {
		boolean isSerieTitle = key.startsWith("\"");
		int idxEndYearDiff = -1;
		int idxStartName = isSerieTitle ? 1 : 0;
		int idxEndName = 1;
		int idxStartYear = 0;
		String title = null;
		if (isSerieTitle) {
			idxEndName = key.indexOf('\"', idxStartName);
			title = key.substring(idxStartName, idxEndName);
			idxStartYear = key.indexOf('(', idxEndName);
		} else {

			idxStartYear = searchYeartStartIdx(key);
			if (idxStartYear == -1)
				title = key.substring(idxStartName);
			else
				title = key.substring(idxStartName, idxStartYear).trim();
		
		}
		productionKey.setTitle(title);
		if (idxStartYear != -1) {

			idxEndYearDiff = key.indexOf(')', idxStartYear);
			int idxSep = key.indexOf('/', idxStartYear);
			if (idxSep > idxEndYearDiff)
				idxSep = -1;
			int idxEndYear = idxSep == -1 ? idxEndYearDiff : idxSep;

			Integer year = null;
			String sYear = key.substring(idxStartYear + 1, idxEndYear);
			if (!sYear.contains("?")) {
				year = Integer.valueOf(sYear);
				productionKey.setYear(year);

			}
		}
		return idxEndYearDiff;
	}

	private static void fillEpisodeNameSeasonNumberInProductionKey(
			ProductionKey productionKey, String key, int fromIdx) {
		int startIdx = key.indexOf("{", fromIdx);
		if (startIdx == -1)
			return;
		int endIdx = key.indexOf("}", startIdx);
		int idxSeasonNumber = key.lastIndexOf("(#", endIdx);
		int idxEpisodeNumber = key.indexOf(')', idxSeasonNumber);

		if ((idxSeasonNumber != -1) && (idxEpisodeNumber != -1)) {
			if (idxSeasonNumber > startIdx + 1) {
				String episodeTitle = key.substring(startIdx + 1,
						idxSeasonNumber).trim();
				productionKey.setEpisodeTitle(episodeTitle);
			}
			int idxSeparator = key.indexOf('.', idxSeasonNumber);
			if (idxSeparator > 0) {
				String seasonNumber = (key.substring(idxSeasonNumber + 2,
						idxSeparator)).trim();
				productionKey.setSeasonNumber(seasonNumber);
				String episodeNumber = (key.substring(idxSeparator + 1,
						idxEpisodeNumber)).trim();
				productionKey.setEpisodeNumber(episodeNumber);
			}
		} else {
			String episodeTitle = key.substring(startIdx + 1, endIdx).trim();
			productionKey.setEpisodeTitle(episodeTitle);
		}
	}

	private static int searchYeartStartIdx(String key) {
		int idxStartYear = -1;
		boolean searchYear = true;
		while (searchYear) {
			idxStartYear = key.indexOf('(', idxStartYear + 1);
			if (idxStartYear != -1) {
				String temp = key.substring(idxStartYear + 1, idxStartYear + 5);
				if (temp.equals("????") || temp.matches("\\d+")) {
					char c = key.charAt(idxStartYear + 5);
					searchYear = (c != ')' && c != '/');
				}
			} else
				searchYear = false;
		}
		return idxStartYear;
	}
	
	public static boolean checkProductionYear(Integer year) {
		boolean flag = year != null && year >= 2000 && year <= 2000;
		return flag;
	}

	/*
	public static void main(String[] args) {
		String line = "\"#1 Single\" (2006)					2006-????";
		String line2 = "\"#Hashtag: The Series\" (2013) {#PartnersInCrime (#2.9)}	2015";

		System.out.println(extractKey(line, 0));
		System.out.println(extractKey(line2, 0));
	} */
}
