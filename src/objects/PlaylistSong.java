package objects;

public class PlaylistSong implements Comparable<PlaylistSong>{
	private int listId;
	private String songName;
	private String youtubeLink;
	private int playCount;
	private boolean played;
	
	public PlaylistSong(String songName, String youtubeLink, int playCount){
		this.songName = songName;
		this.youtubeLink = youtubeLink;
		this.playCount = playCount;
	}

	public int getPlayCount() {
		return playCount;
	}
	public int getListId() {
		return listId;
	}
	public void setListId(int listId) {
		played = true;
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
	
	public String toString(){
		return songName + " " + youtubeLink;
	}
	
	@Override
	public int compareTo(PlaylistSong compareSong) {
		if(!played){
			int comparePlayCount = compareSong.getPlayCount();
			return comparePlayCount - this.playCount;
		}
		
		int compareId = compareSong.getListId();
		//ascending order
		return this.listId - compareId;
		//descending order
		//return compareId - this.listId;
	}	
	
	@Override
	public boolean equals(Object object){
		boolean result = false;
		if(object == null || object.getClass() != getClass()){
			result = false;
		} 
		else{
			PlaylistSong compareSong = (PlaylistSong) object;
			if(this.songName.equals(compareSong.songName)){
				result = true;
			}
		}
		return result;
	}
}
