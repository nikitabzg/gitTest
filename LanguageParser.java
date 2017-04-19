package be.ulb.imdb.ba2.dataImport.reader.language;

import be.ulb.imdb.ba2.dataImport.reader.production.ProductionParser;

public class LanguageParser {

	public LanguageLine parseLanguage(String line){
		LanguageLine languageLine = new LanguageLine();
		String key = ProductionParser.extractKey(line, 0);
		String language=null;
		int idx= key.length();
		int idx2=line.indexOf("(", idx);
		if(idx2==-1)
			language=line.substring(idx).trim();
		else
			language=line.substring(idx, idx2).trim();
		languageLine.setKey(key);
		languageLine.setLanguage(language);
		return languageLine;
	}
}
