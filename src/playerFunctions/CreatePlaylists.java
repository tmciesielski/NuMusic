 package playerFunctions;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

import objects.Playlist;
import objects.PlaylistSong;
import objects.SongPref;
import objects.XmSong;
import tables.XmTable;
import tables.PrefTable;
import tables.YoutubeTable;
import database.MusicDB;

public class CreatePlaylists {

	// All playlist should be descending XM playcount modified by personal preference
	public static ArrayList<PlaylistSong> createAllPlaylist() throws ClassNotFoundException, SQLException, ParseException {
		Playlist list = new Playlist();
		ArrayList<PlaylistSong> playlist = list.getPlaylist(4, 4, 4, 4);
		return playlist;
		
		/*
		Connection conn = MusicDB.getConnection();
		XmTable musicTable = new XmTable(conn);
		PrefTable prefTable = new PrefTable(conn);
		YoutubeTable youtubeTable = new YoutubeTable(conn);
		ArrayList<XmSong> songs = musicTable.getSongs();
		
		int listId = 0;
		for(PlaylistSong song : playlist) {
			// give each song a number, modify it based on pref table
			String songName = song.getSongName();
			SongPref pref = prefTable.getSongPref(songName);
			listId = listId + (pref.getIdMod() * -1);
			
			// get the song's youtube link
			String youtubeLink = youtubeTable.getSongLink(songName);
			
			// add it to the playlist
			
			PlaylistSong pSong = new PlaylistSong();
			pSong.setListId(listId);
			pSong.setSongName(songName);
			pSong.setYoutubeLink(youtubeLink);
			
			playlist.add(pSong);		
		}
		
		//sort the playlist
		Collections.sort(playlist);
		Playlist allPlaylist = new Playlist();
		allPlaylist.setPlaylist(playlist);
		
		return allPlaylist;
		*/
	}
	
	public static int modListIdByPref (String songName, int listId, Connection conn) throws SQLException, ParseException {
		int newId = listId;
		


		
		return newId;
	}
}
