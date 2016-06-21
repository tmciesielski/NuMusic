package objects;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Playlist {
	private ArrayList<PlaylistSong> playlist = new ArrayList<PlaylistSong>();

	public ArrayList<PlaylistSong> getPlaylist() {
		return playlist;
	}

	public void setPlaylist(ArrayList<PlaylistSong> playlist) {
		this.playlist = playlist;
	}
	
	public Document toXML() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		// root playlist element	
		Document xmlDoc = docBuilder.newDocument();
		Element rootElement = xmlDoc.createElement("PLAYLIST");
		xmlDoc.appendChild(rootElement);
		
		for(PlaylistSong song : playlist) {
			// song element
			Element songElement = xmlDoc.createElement("SONG");
			rootElement.appendChild(songElement);
			
			// song id node
			Element idNode = xmlDoc.createElement("ID");
			idNode.appendChild(xmlDoc.createTextNode(Integer.toString(song.getListId())));
			songElement.appendChild(idNode);
			
			// song name node
			Element nameNode = xmlDoc.createElement("NAME");
			nameNode.appendChild(xmlDoc.createTextNode(song.getSongName()));
			songElement.appendChild(nameNode);
			
			// song id node
			Element linkNode = xmlDoc.createElement("YOUTUBE_LINK");
			linkNode.appendChild(xmlDoc.createTextNode(song.getYoutubeLink()));
			songElement.appendChild(linkNode);
		}
		
		return xmlDoc;
	}
}
