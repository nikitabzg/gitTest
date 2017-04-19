package be.ulb.imdb.ba2.dataImport.reader.language;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import be.ulb.imdb.ba2.dataImport.reader.FileReader;
import be.ulb.imdb.ba2.dataImport.reader.ProductionKey;
import be.ulb.imdb.ba2.dataImport.reader.production.ProductionParser;

public class LanguageImporter {

	private LanguageParser languageParser = new LanguageParser();

	public static final String suspendedCode = "{{SUSPENDED}}";

	public void importLanguages(String filePath) {

		List<String> distinctLanguages = new ArrayList<String>();

		FileReader reader = new FileReader(filePath);
		BufferedReader br = reader.readFile();
		String line = "";
		int i = 0;
		while (line != null) {
			try {
				line = br.readLine();
				i++;
				if (line != null && !line.contains(suspendedCode)) {
					LanguageLine languageLine=languageParser.parseLanguage(line);
					System.out.println(languageLine);
					ProductionKey productionKey= ProductionParser.parseKey(languageLine.getKey());
					//check year
					
					//check if it is a new language to insert
					if(!distinctLanguages.contains(languageLine.getLanguage())){
						System.out.println("new language "+languageLine.getLanguage());
						//save it into the database
					}
					//do the rest
				}
			} catch (Exception e) {
				System.out.println("Error reading line: " + line);
				throw new RuntimeException(e);
			}
		}
	}
	
	public static void main(String[] args) {
		new LanguageImporter().importLanguages("languages.txt");
	}
}
