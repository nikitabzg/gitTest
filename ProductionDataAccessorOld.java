package be.ulb.imdb.ba2.dataImport.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import be.ulb.imdb.ba2.dataImport.model.Episode;
import be.ulb.imdb.ba2.dataImport.model.Movie;
import be.ulb.imdb.ba2.dataImport.model.Production;
import be.ulb.imdb.ba2.dataImport.model.Serie;
import be.ulb.imdb.ba2.dataImport.reader.EpisodeLine;
import be.ulb.imdb.ba2.dataImport.reader.ProductionBaseLine;

public class ProductionDataAccessorOld extends DataAccessor {

	private void insertProduction(Production p, Connection conn) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("insert into oeuvre set titre=?, annee=?, diff=?",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, p.getTitle());
			if (p.getYear() != null) {
				st.setInt(2, p.getYear());
			} else {
				st.setNull(2, Types.INTEGER);
			}
			st.setString(3, p.getDiff());
			st.executeUpdate();
			rs = st.getGeneratedKeys();

			if (rs.next()) {
				int id = rs.getInt(1);
				p.setId(id);
			} else {

				// throw an exception from here
			}
		} finally {
			if ((st != null)) {
				try {
					st.close();
				} catch (Exception e) {
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
				}
			}
		}

	}

	public void saveMovie(Movie movie) throws SQLException {
		Connection conn = null;
		java.sql.Statement st = null;
		try {
			conn = getConnection();
			insertProduction(movie, conn);
			st = conn.createStatement();
			st.executeUpdate("insert into film set id_film=" + movie.getId());
		} finally {
			if (st != null) {
				try {
					st.close();
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

	public void saveSerie(Serie serie) throws SQLException {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = getConnection();
			insertProduction(serie, conn);
			st = conn.prepareStatement("insert into serie set annee_fin=?, id_serie=?");
			if (serie.getEndYear() != null)
				st.setInt(1, serie.getEndYear());
			else
				st.setNull(1, Types.INTEGER);
			st.setInt(2, serie.getId());
			st.executeUpdate();
		} finally {
			if (st != null) {
				try {
					conn.close();
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

	public void saveEpisode(Episode episode) throws SQLException {

		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = getConnection();
			insertProduction(episode, conn);
			st = conn.prepareStatement(
					"insert into episode set id_episode=?, numero_saison=?, numero_episode=?, id_serie=?");
			st.setInt(1, episode.getId());
			if (episode.getSeasonNumber() != null) {
				st.setString(2, episode.getSeasonNumber());
			} else {
				st.setNull(2, Types.VARCHAR);
			}
			if (episode.getEpisodeNumber() != null) {
				st.setString(3, episode.getEpisodeNumber());
			} else {
				st.setNull(3, Types.VARCHAR);
			}
			st.setInt(4, episode.getSerieID());
			st.executeUpdate();
		} finally {
			if (st != null) {
				try {
					st.close();
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

	public void updateMovie(String title, Integer year, String diff, Float rank) throws SQLException {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = getConnection();
			String command = "update oeuvre as o inner join film as f on o.identifiant=f.id_film"
					+ " set note = ? where  o.titre=? and o.annee=? and o.diff=?";
			st = conn.prepareStatement(command);
			st.setFloat(1, rank);
			st.setString(2, title);
			st.setInt(3, year);
			st.setString(4, diff);
			int x = st.executeUpdate();
		} finally {
			if (st != null) {
				try {
					st.close();
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

	public void updateSerie(String title, Integer year, String diff, Float rank) throws SQLException {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = getConnection();
			String command = "update oeuvre as o inner join serie as s on o.identifiant=s.id_serie"
					+ " set note = ? where  o.titre=? and o.annee=? and o.diff=?";
			st = conn.prepareStatement(command);
			st.setFloat(1, rank);
			st.setString(2, title);
			st.setInt(3, year);
			st.setString(4, diff);
			int x = st.executeUpdate();
		} finally {
			if (st != null) {
				try {
					st.close();
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

	public void updateEpisode(String serieTitle, String episodeTitle, Integer serieYear, String diff, Float rank)
			throws SQLException {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = getConnection();
			String command = "update oeuvre as o inner join episode as e on o.identifiant=e.id_episode "
					+ "inner join serie s on s.id_serie = e.id_serie "
					+ "inner join oeuvre o2 on s.id_serie = o2.identifiant " + "set o.note = ? "
					+ "where  o2.annee=? and o2.titre=? and o2.diff=? and ";
			if (episodeTitle == null)
				command += "o.titre is null";
			else
				command += "o.titre=?";

			st = conn.prepareStatement(command);
			st.setFloat(1, rank);
			st.setInt(2, serieYear);
			st.setString(3, serieTitle);
			st.setString(4, diff);
			if (episodeTitle != null)
				st.setString(5, episodeTitle);
			int x = st.executeUpdate();
		} finally {
			if (st != null) {
				try {
					st.close();
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

	public List<Integer> getFilmIds(Connection conn, List<ProductionBaseLine> filmBaseDatas) throws SQLException {
		List<Integer> ids = new ArrayList<Integer>();

		if (!filmBaseDatas.isEmpty()) {
			PreparedStatement st = null;

			try {
				st = conn.prepareStatement("select O.id from oeuvre o inner jon film f on o.id=f.id_film"
						+ " where o.titre=? and o.annee=? and o.diff=?");
				for (ProductionBaseLine line : filmBaseDatas) {
					Integer id = null;
					st.setString(1, line.getTitle());
					st.setInt(2, line.getYear());
					st.setString(3, line.getDiff());
					ResultSet rs = null;
					try {
						rs = st.executeQuery();
						if (rs.next()) {
							id = rs.getInt(1);
						}
						ids.add(id);
					} finally {
						if (rs != null)
							try {
								rs.close();
							} catch (Exception e) {
								// TODO: handle exception
							}
					}
				}
			} finally {
				if (st != null) {
					try {
						st.close();
					} catch (Exception ex) {

					}
				}
			}
		}
		return ids;
	}
	
	public List<Integer> getSeriesIds(Connection conn, List<ProductionBaseLine> filmBaseDatas) throws SQLException {
		List<Integer> ids = new ArrayList<Integer>();

		if (!filmBaseDatas.isEmpty()) {
			PreparedStatement st = null;

			try {
				st = conn.prepareStatement("select O.id from oeuvre o inner jon serie s on o.id=s.id_serie"
						+ " where o.titre=? and o.annee=? and o.diff=?");
				for (ProductionBaseLine line : filmBaseDatas) {
					Integer id = null;
					st.setString(1, line.getTitle());
					st.setInt(2, line.getYear());
					st.setString(3, line.getDiff());
					ResultSet rs = null;
					try {
						rs = st.executeQuery();
						if (rs.next()) {
							id = rs.getInt(1);
						}
						ids.add(id);
					} finally {
						if (rs != null)
							try {
								rs.close();
							} catch (Exception e) {
								// TODO: handle exception
							}
					}
				}
			} finally {
				if (st != null) {
					try {
						st.close();
					} catch (Exception ex) {

					}
				}
			}
		}
		return ids;
	}
	
	public List<Integer> getEpisodeIds(Connection conn, List<ProductionBaseLine> filmBaseDatas, List<EpisodeLine> episodeLines) throws SQLException {
		List<Integer> ids = new ArrayList<Integer>();

		if (!filmBaseDatas.isEmpty()) {
			PreparedStatement st = null;

			try {
				String command = "select o.id from oeuvre  o inner join episode as e on o.identifiant=e.id_episode "
						+ "inner join serie s on s.id_serie = e.id_serie "
						+ "inner join oeuvre o2 on s.id_serie = o2.identifiant "
						+ "where  o2.annee=? and o2.titre=? and o2.diff=? and ";
				/*if (episodeTitle == null)
					command += "o.titre is null";
				else
					command += "o.titre=?"; */
				
				st = conn.prepareStatement("select O.id from oeuvre o inner jon serie s on o.id=s.id_serie"
						+ " where o.titre=? and o.annee=? and o.diff=?");
				for (ProductionBaseLine line : filmBaseDatas) {
					Integer id = null;
					st.setString(1, line.getTitle());
					st.setInt(2, line.getYear());
					st.setString(3, line.getDiff());
					ResultSet rs = null;
					try {
						rs = st.executeQuery();
						if (rs.next()) {
							id = rs.getInt(1);
						}
						ids.add(id);
					} finally {
						if (rs != null)
							try {
								rs.close();
							} catch (Exception e) {
								// TODO: handle exception
							}
					}
				}
			} finally {
				if (st != null) {
					try {
						st.close();
					} catch (Exception ex) {

					}
				}
			}
		}
		return ids;
	}
}
