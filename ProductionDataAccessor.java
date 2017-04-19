package be.ulb.imdb.ba2.dataImport.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;

import be.ulb.imdb.ba2.dataImport.model.Episode;
import be.ulb.imdb.ba2.dataImport.model.Movie;
import be.ulb.imdb.ba2.dataImport.model.Production;
import be.ulb.imdb.ba2.dataImport.model.Serie;
import be.ulb.imdb.ba2.dataImport.reader.ranking.RankingLine;
//import be.ulb.imdb.ba2.dataImport.reader.EpisodeLine;

public class ProductionDataAccessor extends DataAccessor {

	public void saveMovies(List<Movie> movies) throws SQLException {
		System.out.println("saving movies");
		Connection conn = null;
		PreparedStatement stProduction = null;
		PreparedStatement stMovie = null;
		try {
			conn=getConnection();
			stProduction = conn.prepareStatement("insert into oeuvre set identifiant=?, titre=?, annee=?");
			stMovie = conn.prepareStatement("insert into film set id_film=?");
			for (Movie m:movies){
				//insertProduction(m, conn);
				stProduction.setString(1, m.getId());
				if (m.getTitle() != null) {
					stProduction.setString(2, m.getTitle());
				} else {
					stProduction.setNull(2, Types.VARCHAR);
				}
				if (m.getYear() != null) {
					stProduction.setInt(3, m.getYear());
				} else {
					stProduction.setNull(3, Types.INTEGER);
				}
				stProduction.executeUpdate();
				
				stMovie.setString(1, m.getId());
				stMovie.executeBatch();
			}
		} finally {
			if (stProduction != null) {
				try {
					stProduction.close();
				} catch (Exception ex) {
				}
			}
			if (stMovie != null) {
				try {
					stMovie.close();
				} catch (Exception ex) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public void saveSeries(List<Serie> series) throws SQLException {
		System.out.println("saving series");
		Connection conn = null;
		PreparedStatement stProduction = null;
		PreparedStatement stSerie = null;
		try{
			conn=getConnection();
			stProduction = conn.prepareStatement("insert into oeuvre set identifiant=?, titre=?, annee=?");
			stSerie = conn.prepareStatement("insert into serie set annee_fin=?, id_serie=?");
			for (Serie s:series){
				//insertProduction(s, conn);
				
				stProduction.setString(1, s.getId());
				if (s.getTitle() != null) {
					stProduction.setString(2, s.getTitle());
				} else {
					stProduction.setNull(2, Types.VARCHAR);
				}
				if (s.getYear() != null) {
					stProduction.setInt(3, s.getYear());
				} else {
					stProduction.setNull(3, Types.INTEGER);
				}
				stProduction.executeUpdate();
				
				if (s.getEndYear() != null)
					stSerie.setInt(1, s.getEndYear());
				else
					stSerie.setNull(1, Types.INTEGER);
				stSerie.setString(2, s.getId());
				stSerie.executeBatch();
			}
		} finally {
			if (stProduction != null) {
				try {
					stProduction.close();
				} catch (Exception ex) {
				}
			}
			if (stSerie != null) {
				try {
					stSerie.close();
				} catch (Exception ex) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
				}
			}
		}
	}
	
	public void saveEpisodes(List<Episode> episodes) throws SQLException{
		System.out.println("saving episodes");
		Connection conn = null;
		PreparedStatement stProduction = null;
		PreparedStatement stEpisode = null;
		try{
			//insertProduction(episode, conn);
			conn = getConnection();
			stProduction = conn.prepareStatement("insert into oeuvre set identifiant=?, titre=?, annee=?");
			stEpisode = conn.prepareStatement("insert into episode set id_episode=?, numero_saison=?, numero_episode=?, id_serie=?");
			
			for (Episode e:episodes){
				
				stProduction.setString(1, e.getId());
				if (e.getTitle() != null) {
					stProduction.setString(2, e.getTitle());
				} else {
					stProduction.setNull(2, Types.VARCHAR);
				}
				if (e.getYear() != null) {
					stProduction.setInt(3, e.getYear());
				} else {
					stProduction.setNull(3, Types.INTEGER);
				}
				stProduction.executeUpdate();
				
				stEpisode.setString(1, e.getId());
				if (e.getSeasonNumber() != null) {
					stEpisode.setString(2, e.getSeasonNumber());
				} else {
					stEpisode.setNull(2, Types.VARCHAR);
				}
				if (e.getEpisodeNumber() != null) {
					stEpisode.setString(3, e.getEpisodeNumber());
				} else {
					stEpisode.setNull(3, Types.VARCHAR);
				}
				stEpisode.setString(4, e.getSeasonNumber());
				stEpisode.executeBatch(); 
			}
		} finally {
			if (stEpisode != null) {
				try {
					stEpisode.close();
				} catch (Exception e) {
				}
			}
			if (stProduction != null) {
				try {
					stProduction.close();
				} catch (Exception e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
				}
			}
		}
	}
	
	public void saveNotes(List<RankingLine> notes) throws SQLException {
		System.out.println(notes.size());
		Connection conn = null;
		PreparedStatement stNotes = null;
		try {
			conn = getConnection();
			stNotes = conn.prepareStatement("update oeuvre as o set note=? where identifiant=?");
			for (RankingLine rl:notes){
				stNotes.setFloat(1, rl.getNote());
				stNotes.setString(2, rl.getKey());
				//System.out.println(stNotes);
				//System.out.println(rl.getNote());
				//System.out.println(rl.getKey());
				stNotes.executeUpdate();
			}
		} finally {
			if (stNotes != null) {
				try {
					stNotes.close();
				} catch (Exception e) {
				}
			} 
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
				}
			}
		}
			
		
	}
	
}
