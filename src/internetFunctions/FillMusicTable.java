package internetFunctions;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import objects.XmSong;
import tables.XmTable;
import tables.RawXmTable;
import database.MusicDB;

public class FillMusicTable {

	public static void consolidateSongInstances() throws ClassNotFoundException, SQLException, ParseException, IOException {
		Connection conn = MusicDB.getConnection();
		RawXmTable rawXmTable = new RawXmTable(conn);
		XmTable xmTable = new XmTable(conn);
		
		ArrayList<String[]> songs = rawXmTable.getSongs();		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); //12 hour
		XmSong xmSong = null;
		
		for(String[] song : songs) {
			String currSong = song[0];
			Date currDate = sdf.parse(song[1]);
			
			// create new xmSong for the 1st instance
			if(songs.indexOf(song) == 0) {
				xmSong = new XmSong(currSong, currDate, currDate, 1);
				
			// compare subsequent song names to the current xmSong
			// if it's the same song name
			} else if(currSong.equals(xmSong.getSongName())) {
				// increment the counter and update the dates
				xmSong.increment(currDate);
				
			// if it's a different song and not the last row
			} else {
				// add the current xmSong to the XM_SONGS table
				xmTable.addSong(xmSong);
				
				// start incrementing a new xmSong
				xmSong = new XmSong(currSong, currDate, currDate, 1);
			}
			
			// if it's the last song, add the current xmSong
			if(songs.indexOf(song)+1 == songs.size()) {
				xmTable.addSong(xmSong);
			}
		}
	}
}
