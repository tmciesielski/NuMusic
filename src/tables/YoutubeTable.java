package tables;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class YoutubeTable {
	private static Connection derbyConn = null;
	private static String tableName = "YOUTUBE_LINKS";
	
	public YoutubeTable(Connection conn) {		
		derbyConn = conn;
	}
	
	public void addSong(String songName, String youtubeLink, String youtubeTitle) throws IOException, SQLException {
		Statement s = derbyConn.createStatement();
		
		songName = songName.replace("'", "''");
		youtubeTitle = youtubeTitle.replace("'", "''");
		
		// Add song if it doesn't exist
		if(!songExists(songName)) {
			System.out.println("Adding: "+youtubeTitle);
			s.execute("insert into "+tableName+" (SONG_NAME, YOUTUBE_LINK, YOUTUBE_TITLE) values"
					+  " ('"+songName+"', '"+youtubeLink+"', '"+youtubeTitle+"')");
			
		// Else throw exception
		} else {
			throw new IOException("Duplicate entry attempted");
		}
		
		s.close();
	}
	
	private boolean songExists (String songName) throws SQLException {
		Statement s = null;
		
		try {
			s = derbyConn.createStatement();
			ResultSet results = s.executeQuery("select * from "+tableName+" where SONG_NAME = "+songName);
			
			if (results.next()) {
				return true;
			}
			
        } catch (SQLException sqle) {
        		return false;
        } finally {
        	s.close();
        }
		
		return false;
	}
	
	public ArrayList<String> getLinks() throws SQLException {
		ArrayList<String> songs = new ArrayList<String>();
		ResultSet results;
		Statement s = null;
		
		try {
			s = derbyConn.createStatement();
			results = s.executeQuery("select YOUTUBE_LINK from "+tableName);
			
			while (results.next()) {
				songs.add(results.getString(1));
			}
        } finally {
        	s.close();
        }
		
		return songs;
	}
	
	public String getSongLink(String songName) throws SQLException {
		String songLink = null;
		ResultSet results;
		Statement s = null;
		
		try {
			s = derbyConn.createStatement();
			results = s.executeQuery("select YOUTUBE_LINK from "+tableName+" where SONG_NAME = '"+songName+"'");
			
			while (results.next()) {
				songLink = results.getString(1);
			}
        } finally {
        	s.close();
        }
		
		return songLink;
	}
}
