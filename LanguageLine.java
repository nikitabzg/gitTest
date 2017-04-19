package be.ulb.imdb.ba2.dataImport.reader.language;

public class LanguageLine {

	private String key;
	private String language;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Override
	public String toString() {
		return "LanguageLine [key=" + key + ", language=" + language + "]";
	}

}
