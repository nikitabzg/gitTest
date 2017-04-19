package be.ulb.imdb.ba2.dataImport.model;

public class Serie extends Production {
	private Integer endYear;

	public Integer getEndYear() {
		return endYear;
	}

	public void setEndYear(Integer endYear) {
		this.endYear = endYear;
	}

	@Override
	public String toString() {
		String s = super.toString()+";end year="+endYear;
		return s;
	}
	
	
	

}
