package be.ulb.imdb.ba2.dataImport.reader;

public class ProductionLine {

	public static char TYPE_MOVIE='m';
	public static char TYPE_SERIE='s';
	public static char TYPE_EPISODE='e';

	private String key;
	private Integer endYear;
	private Integer episodeYear;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getEndYear() {
		return endYear;
	}

	public void setEndYear(Integer endYear) {
		this.endYear = endYear;
	}

	public Integer getEpisodeYear() {
		return episodeYear;
	}

	public void setEpisodeYear(Integer episodeYear) {
		this.episodeYear = episodeYear;
	}

	public char getType() {
		return ProductionKey.getProductionType(key);
	}

	@Override
	public String toString() {
		return "ProductionLine [key=" + key + ", endYear=" + endYear
				+ ", episodeYear=" + episodeYear + "]";
	}

}
