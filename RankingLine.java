package be.ulb.imdb.ba2.dataImport.reader.ranking;

public class RankingLine {

	private String key;
	private Float note;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Float getNote() {
		return note;
	}

	public void setNote(Float note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "RankingLine [key=" + key + ", note=" + note + "]";
	}

}
