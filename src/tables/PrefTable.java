package tables;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import objects.SongPref;

public class PrefTable {
	private static Connection derbyConn = null;
	private static String tableName = "PREF";
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public PrefTable(Connection conn) {		
		derbyConn = conn;
	}
	
	public SongPref getSongPref(String songName) throws SQLException, ParseException {
		SongPref pref = new SongPref();
		ResultSet results;
		Statement s = null;
		
		try {
			s = derbyConn.createStatement();
			results = s.executeQuery("select * from "+tableName+" where SONG_NAME = '"+songName+"'");
			
			while (results.next()) {
	  			pref.setSongName(results.getString(1));
				pref.setPlayDate(sdf.parse(results.getString(2)));
				pref.setPlus(Integer.parseInt(results.getString(3)));
				pref.setMinus(Integer.parseInt(results.getString(4)));
				pref.setUpvote(Integer.parseInt(results.getString(5)));
				pref.setDownvote(Integer.parseInt(results.getString(6)));
				pref.setPlayCount(Integer.parseInt(results.getString(7)));
				pref.setTag(results.getString(8));
			}
        } finally {
        	s.close();
        }
		
		return pref;
	}
}
