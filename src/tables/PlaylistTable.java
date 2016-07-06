 package tables;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import objects.XmSong;

public class PlaylistTable {
	private static Connection derbyConn = null;
	private static String tableName = "YOUTUBE_LINKS";
	private int size;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); //12 hour
	
	public PlaylistTable(Connection conn) throws SQLException {		
		derbyConn = conn;
		Statement s = derbyConn.createStatement();
		ResultSet results = s.executeQuery("select * from "+tableName);
		while(results.next()) {
		    size++;
		}
	}
	
	public void addSong(String songName, String youtubeLink, String youtubeTitle, int viewCount, Date newestDate, Date oldestDate, int playCount) throws IOException, SQLException {
		Statement s = derbyConn.createStatement();
		
		songName = songName.replace("'", "''");
		youtubeTitle = youtubeTitle.replace("'", "''");
		
		// Add song if it doesn't exist
		if(!songExists(songName)) {
			System.out.println("Adding: "+youtubeTitle);
			s.execute("insert into "+tableName+" (SONG_NAME, YOUTUBE_LINK, YOUTUBE_TITLE, VIEW_COUNT, NEWEST_DATE, OLDEST_DATE, PLAY_COUNT) values"
					+  " ('"+songName+"', '"+youtubeLink+"', '"+youtubeTitle+"', "+viewCount+", '"+sdf.format(newestDate)+"', '"+sdf.format(oldestDate)+"', "+playCount+")");
			
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
	
	public ArrayList<String[]> getLinks() throws SQLException {
		ArrayList<String[]> songs = new ArrayList<String[]>();
		ResultSet results;
		Statement s = null;
		
		try {
			s = derbyConn.createStatement();
			results = s.executeQuery("select SONG_NAME, YOUTUBE_LINK, YOUTUBE_TITLE, PLAY_COUNT from "+tableName);
			
			while (results.next()) {
				songs.add(new String[] {results.getString(1), results.getString(2), results.getString(3), results.getString(4)});
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
	
	public ArrayList<String[]> getSpecificSongs(String option) throws SQLException, ParseException {
		ArrayList<String[]> songs = new ArrayList<String[]>();
		ResultSet results;
		Statement s = null;
		
		try {
			s = derbyConn.createStatement();
			//* has id, artist, title, song, newest, oldest, count
			results = s.executeQuery("select SONG_NAME, YOUTUBE_LINK, PLAY_COUNT from "+tableName+" order by " + option + " FETCH NEXT " + (int)Math.floor(size * .1) + " ROWS ONLY");
			while (results.next()) {
				songs.add(new String[] {results.getString(1), results.getString(2), results.getString(3)});
			}
        } finally {
        	s.close();
        }
		
		return songs;
	}
}
