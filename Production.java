package be.ulb.imdb.ba2.dataImport.model;

import java.util.Date;

public class Production {
	
	protected String id;
	private String title;
	private Integer year;
	private Float note;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public Float getNote() {
		return note;
	}
	public void setNote(Float note) {
		this.note = note;
	}
	
	@Override
	public String toString() {
		String s = "id="+id+";title="+title+";year="+year+";note="+note;
		return s;
	}
	
	
	
	
}
