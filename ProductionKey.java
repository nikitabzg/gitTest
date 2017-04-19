package be.ulb.imdb.ba2.dataImport.reader;

public class ProductionKey {

	private String title;
	private Integer year;
	private String episodeTitle;
	private String seasonNumber;
	private String episodeNumber;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getEpisodeTitle() {
		return episodeTitle;
	}

	public void setEpisodeTitle(String episodeTitle) {
		this.episodeTitle = episodeTitle;
	}

	public String getSeasonNumber() {
		return seasonNumber;
	}

	public void setSeasonNumber(String seasonNumber) {
		this.seasonNumber = seasonNumber;
	}

	public String getEpisodeNumber() {
		return episodeNumber;
	}

	public void setEpisodeNumber(String episodeNumber) {
		this.episodeNumber = episodeNumber;
	}

	public static char getProductionType(String productionKey) {
		if (productionKey.startsWith("\"")) {
			if (productionKey.indexOf("\"", 1) < productionKey.indexOf("{"))
				return ProductionLine.TYPE_EPISODE;
			return ProductionLine.TYPE_SERIE;
		}
		return ProductionLine.TYPE_MOVIE;
	}

	@Override
	public String toString() {
		return "ProductionKey [title=" + title + ", year=" + year
				+ ", episodeTitle=" + episodeTitle + ", seasonNumber="
				+ seasonNumber + ", episodeNumber=" + episodeNumber + "]";
	}
	
	
	
	

}
