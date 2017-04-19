package be.ulb.imdb.ba2.dataImport.reader;

public class InvalidLineException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7261578005230594989L;
	private String line;

	public InvalidLineException(String line) {
		super();
		this.line = line;
	}

	@Override
	public String getMessage() {
		return "Invalid line: "+line;
	}
	
	
}
