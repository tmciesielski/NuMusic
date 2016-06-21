package objects;

public class PlaylistSong implements Comparable<PlaylistSong>{
	private int listId;
	private String songName;
	private String youtubeLink;
	
	public int getListId() {
		return listId;
	}
	public void setListId(int listId) {
		this.listId = listId;
	}
	public String getSongName() {
		return songName;
	}
	public void setSongName(String songName) {
		this.songName = songName;
	}
	public String getYoutubeLink() {
		return youtubeLink;
	}
	public void setYoutubeLink(String youtubeLink) {
		this.youtubeLink = youtubeLink;
	}
	
	@Override
	public int compareTo(PlaylistSong compareSong) {
		
		int compareId = compareSong.getListId();
		
		//ascending order
		return this.listId - compareId;
		//descending order
		//return compareId - this.listId;
	}	
}
