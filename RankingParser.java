package be.ulb.imdb.ba2.dataImport.reader.ranking;

import java.util.Arrays;

import be.ulb.imdb.ba2.dataImport.reader.production.ProductionParser;

public class RankingParser {

	public RankingLine parse(String line) {
		RankingLine rankingLine = new RankingLine();
		String[] items = line.split("\\s+");
		rankingLine.setNote(Float.valueOf(items[3]));
		int idx=line.indexOf(items[4]);
		String key=ProductionParser.extractKey(line, idx);
		rankingLine.setKey(key);
		return rankingLine;
	}

}
