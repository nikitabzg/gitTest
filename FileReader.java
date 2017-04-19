package be.ulb.imdb.ba2.dataImport.reader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileReader {
	private String filePath;
	
	
	public FileReader(String filePath) {
		super();
		this.filePath = filePath;
	}


	public BufferedReader readFile(){
		InputStream is= this.getClass().getClassLoader().getResourceAsStream(filePath);
		BufferedReader b1 = new BufferedReader(new InputStreamReader(is));
		return b1;
	}

}
