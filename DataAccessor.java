package be.ulb.imdb.ba2.dataImport.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataAccessor {
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception ex) {
			// handle the error
		}
	}

	protected Connection getConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:mysql://localhost/imdb?useSSL=false", "root", "root");
	}
}
