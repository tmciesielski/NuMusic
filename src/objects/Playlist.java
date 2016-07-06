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
import tables.YoutubeTable;

public class Playlist {
	private ArrayList<PlaylistSong> playlist = new ArrayList<PlaylistSong>();

	public ArrayList<PlaylistSong> getPlaylist(int count, int newDate, int oldDate, int random) throws ClassNotFoundException, SQLException, ParseException {
		Connection conn = MusicDB.getConnection();
		YoutubeTable youtubeTable = new YoutubeTable(conn);
		//PlaylistTable playlistTable = new PlaylistTable(conn);
		
		//SONG_NAME, YOUTUBE_LINK, PLAY_COUNT in the string array
		/*ArrayList<String[]> songsByCount = youtubeTable.getSpecificSongs("PLAY_COUNT DESC");
		ArrayList<String[]> songsByNewDate = youtubeTable.getSpecificSongs("NEWEST_DATE DESC");
		ArrayList<String[]> songsByOldDate = youtubeTable.getSpecificSongs("NEWEST_DATE ASC");
		ArrayList<String[]> songsByRandom = youtubeTable.getSpecificSongs("RANDOM()");*/
		
		ArrayList<String[]> songsByCount = youtubeTable.getTestSongs("PLAY_COUNT DESC");
		
		addSongs(songsByCount, count);
		
		return playlist;
	}
	
	
	
	public void addSongs(ArrayList<String[]> songsToAdd, int number){
		ArrayList<Integer> numbers = new ArrayList<Integer>();     
		for(int i = 0; i < songsToAdd.size(); i++){       
			numbers.add(i);     
		}      
		Collections.shuffle(numbers);
		for(int i = 0; i < number; i++){
			String[] song = songsToAdd.get(numbers.get(i));
			PlaylistSong playlistSong = new PlaylistSong(song[0], song[1], Integer.parseInt(song[2]));
			if(!playlist.contains(playlistSong)){
				playlist.add(playlistSong);
			}
			else{
				number++;
			}
		}
		Collections.sort(playlist);
		for(int i = 1; i <= playlist.size(); i++){
			playlist.get(i - 1).setListId(i);
		}
	}

	public void setPlaylist(ArrayList<PlaylistSong> playlist) {
		this.playlist = playlist;
	}
	
	public Document toXML(ArrayList<PlaylistSong> playlist) throws ParserConfigurationException, TransformerException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		// root playlist element	
		Document xmlDoc = docBuilder.newDocument();
		Element rootElement = xmlDoc.createElement("PLAYLIST");
		xmlDoc.appendChild(rootElement);
		
		for(PlaylistSong song : playlist) {
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
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(xmlDoc);
		StreamResult result = new StreamResult(new File("C:\\Eclipse\\workspace\\NuMusic\\music.xml"));
		
		transformer.transform(source, result);

		System.out.println("File saved!");
		
		return xmlDoc;
	}
}
