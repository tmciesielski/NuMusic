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

public class XmTable {
	private static Connection derbyConn = null;
	private static String tableName = "XM_SONGS";
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); //12 hour
	
	public XmTable(Connection conn) {
		derbyConn = conn;
	}
	
	public void addSong(XmSong xmSong) throws IOException, SQLException {
		Statement s = derbyConn.createStatement();
		String song = xmSong.getSongName();
		Date newestDate = xmSong.getNewestDate();
		Date oldestDate = xmSong.getOldestDate();
		int playCount = xmSong.getPlayCount();

		try {
			// Add song if it doesn't exist
			if(!songExists(xmSong)) {
				System.out.println("Adding: "+song);
				song = song.replace("'", "''");
				s.executeUpdate("insert into "+tableName+" (SONG, NEWEST_DATE, OLDEST_DATE, PLAY_COUNT) values"
						+  " ('"+song+"', '"+sdf.format(newestDate)+"', '"+sdf.format(oldestDate)+"', "+playCount+")");
				
			// Else throw exception
			} else {
				throw new IOException("Duplicate entry attempted");
			}
		
        } catch (SQLException sqle) {
        	throw sqle;
	    } finally {
	    	s.close();
	    }
	}
	
	private boolean songExists (XmSong xmSong) throws SQLException {
		Statement s = null;
		String song = xmSong.getSongName();
		
		try {
			s = derbyConn.createStatement();
			ResultSet results = s.executeQuery("select * from "+tableName+" where SONG = '"+song+"'");
			
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
	
	public ArrayList<XmSong> getSongs() throws SQLException, ParseException {
		ArrayList<XmSong> songs = new ArrayList<XmSong>();
		ResultSet results;
		Statement s = null;
		
		try {
			s = derbyConn.createStatement();
			results = s.executeQuery("select * from "+tableName+" order by PLAY_COUNT desc, NEWEST_DATE desc");
			
			while (results.next()) {
				XmSong song = new XmSong(results.getString(2), sdf.parse(results.getString(3)), 
						sdf.parse(results.getString(4)), Integer.parseInt(results.getString(5)));
				songs.add(song);
			}
        } finally {
        	s.close();
        }
		
		return songs;
	}
}
