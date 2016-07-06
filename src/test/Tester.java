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

import tables.XmTable;
import tables.YoutubeTable;
import database.MusicDB;

public class Tester {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException, ParseException, ParserConfigurationException, TransformerException {
		Playlist playlist = new Playlist();
		ArrayList<PlaylistSong> songs = playlist.getPlaylist(4, 4, 4, 4);
		System.out.println("Working?");
		playlist.toXML(songs);
	}

}
