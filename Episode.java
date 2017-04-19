package be.ulb.imdb.ba2.dataImport.model;

public class Episode extends Production {
	private String seasonNumber;
	private String episodeNumber;
	private String serieID; 
	
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
	public String getSerieID() {
		return serieID;
	}
	public void setSerieID(String serieID) {
		this.serieID = serieID;
	}
	@Override
	public String toString() {
		String s = super.toString()+";season number="+seasonNumber+";episode number="+episodeNumber;
		return s;
	}
	
	
}
