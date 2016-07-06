package test;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import objects.Playlist;
import objects.PlaylistSong;
import objects.XmSong;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import tables.PlaylistTable;
import tables.XmTable;
import tables.YoutubeTable;
import database.MusicDB;

public class Tester {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException, ParseException, ParserConfigurationException, TransformerException {
//		for(int i = 0; i < 15; i++){
//			Connection conn = MusicDB.getConnection();
//			PlaylistTable playlistTable = new PlaylistTable(conn);
//			System.out.println(playlistTable.getSize());
//			Playlist playlist = new Playlist();
//			ArrayList<PlaylistSong> songs = playlist.getPlaylist(4, 4, 7);
//			for(PlaylistSong song: songs){
//				System.out.println(song.getSongName());
//			}
//			System.out.println(playlistTable.getSize());
//			//playlist.toXML(songs);
//		}
		
//		Playlist playlist = new Playlist();
//		ArrayList<ArrayList<PlaylistSong>> songs = new ArrayList<ArrayList<PlaylistSong>>();
//		for(int i = 0; i < 3; i++){
//			songs = playlist.getPlaylist(4, 4, 7);
//		}
//		playlist.toXML(songs);
//		System.out.println("SAVED");

		
		Connection conn = MusicDB.getConnection();
		YoutubeTable youtubeTable = new YoutubeTable(conn);
		youtubeTable.getAllSongsToAddToPlaylistTable();
		PlaylistTable playlistTable = new PlaylistTable(conn);
		System.out.println(playlistTable.getSize());
	}

}
