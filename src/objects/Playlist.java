package objects;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import database.MusicDB;
import tables.XmTable;
import tables.PlaylistTable;

public class Playlist {
	private ArrayList<ArrayList<PlaylistSong>> playlist = new ArrayList<ArrayList<PlaylistSong>>();
	private ArrayList<PlaylistSong> singlePlaylist = new ArrayList<PlaylistSong>();
	
	public ArrayList<ArrayList<PlaylistSong>> getPlaylist(int count, int newDate, int random) throws ClassNotFoundException, SQLException, ParseException {
		Connection conn = MusicDB.getConnection();
		PlaylistTable playlistTable = new PlaylistTable(conn);
		//PlaylistTable playlistTable = new PlaylistTable(conn);
		
		//SONG_NAME, YOUTUBE_LINK, PLAY_COUNT in the string array
		ArrayList<String[]> songsByCount = playlistTable.getSpecificSongs("PLAY_COUNT DESC");
		ArrayList<String[]> songsByNewDate = playlistTable.getSpecificSongs("NEWEST_DATE DESC");
		ArrayList<String[]> songsByRandom = playlistTable.getSpecificSongs("RANDOM()");
		
		addSongs(songsByCount, count);
		addSongs(songsByNewDate, newDate);
		addSongs(songsByRandom, random);
		
		Collections.sort(singlePlaylist);
		for(int i = 1; i <= singlePlaylist.size(); i++){
			singlePlaylist.get(i - 1).setListId(i);
		}
		playlist.add(singlePlaylist);
		singlePlaylist = new ArrayList<PlaylistSong>();
		return playlist;
	}
	
	
	
	public void addSongs(ArrayList<String[]> songsToAdd, int number) throws SQLException, ClassNotFoundException{
		Connection conn = MusicDB.getConnection();
		PlaylistTable playlistTable = new PlaylistTable(conn);
		ArrayList<Integer> numbers = new ArrayList<Integer>();     
		for(int i = 0; i < songsToAdd.size(); i++){       
			numbers.add(i);     
		}      
		Collections.shuffle(numbers);
		for(int i = 0; i < number; i++){
			String[] song = songsToAdd.get(numbers.get(i));
			PlaylistSong playlistSong = new PlaylistSong(song[1], song[2], Integer.parseInt(song[3]));
			if(!singlePlaylist.contains(playlistSong)){
				playlistTable.removeSong(song[0]);
				singlePlaylist.add(playlistSong);
			}
			else{
				number++;
			}
		}
	}
	
	public Document toXML(ArrayList<ArrayList<PlaylistSong>> playlistSongs) throws ParserConfigurationException, TransformerException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		// root playlist element	
		Document xmlDoc = docBuilder.newDocument();
		Element rootElement = xmlDoc.createElement("PLAYLIST");
		xmlDoc.appendChild(rootElement);
		
		for(ArrayList<PlaylistSong> songList : playlistSongs) {
			for(PlaylistSong song: songList){
				String songName = song.getSongName();
				songName.replace("A-Trak", "A Trak");
				songName.replace("Ne-Yo", "Ne Yo");
				songName.replace("C-Ro", "C Ro");
				songName.replace("Blink-182", "Blink 182");
				songName.replace("Keys-N-Crates", "Keys N Crates");
							
				String[] split = song.getSongName().split("-");
				
				String artist = split[0].trim();
				String title = split[1].trim();
				// song element
				Element songElement = xmlDoc.createElement("SONG");
				rootElement.appendChild(songElement);
				
				// song id node
				Element idNode = xmlDoc.createElement("ID");
				idNode.appendChild(xmlDoc.createTextNode(Integer.toString(song.getListId())));
				songElement.appendChild(idNode);
				
				// song artist name node
				Element artistNameNode = xmlDoc.createElement("ARTIST_NAME");
				artistNameNode.appendChild(xmlDoc.createTextNode(artist));
				songElement.appendChild(artistNameNode);
				
				// song song name node
				Element songNameNode = xmlDoc.createElement("SONG_NAME");
				songNameNode.appendChild(xmlDoc.createTextNode(title));
				songElement.appendChild(songNameNode);
				
				// song id node
				Element linkNode = xmlDoc.createElement("YOUTUBE_LINK");
				linkNode.appendChild(xmlDoc.createTextNode(song.getYoutubeLink()));
				songElement.appendChild(linkNode);
			}
		}
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(xmlDoc);
		StreamResult result = new StreamResult(new File("C://College//FreshmenSummer//NuMusic//WebContent//music.xml"));
		
		transformer.transform(source, result);

		System.out.println("File saved!");
		
		return xmlDoc;
	}
}
