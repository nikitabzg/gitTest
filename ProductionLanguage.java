package be.ulb.imdb.ba2.dataImport.model;

public class ProductionLanguage {

	private int productionId;
	private String language;

	public ProductionLanguage(int productionId, String language) {
		super();
		this.productionId = productionId;
		this.language = language;
	}

	public int getProductionId() {
		return productionId;
	}

	public void setProductionId(int productionId) {
		this.productionId = productionId;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	

}
