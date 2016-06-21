package objects;

import java.util.Date;

public class XmSong {
	private String songName;
	private Date oldestDate;
	private Date newestDate;
	private int playCount;
	
	public XmSong(String songName, Date newestDate, Date oldestDate, int playCount) {
		super();
		this.songName = songName;
		this.newestDate = newestDate;
		this.oldestDate = oldestDate;
		this.playCount = playCount;
	}
	
	public String getSongName() {
		return songName;
	}
	public void setSongName(String songName) {
		this.songName = songName;
	}
	public Date getNewestDate() {
		return newestDate;
	}
	public void setNewestDate(Date newestDate) {
		this.newestDate = newestDate;
	}
	public Date getOldestDate() {
		return oldestDate;
	}
	public void setOldestDate(Date oldestDate) {
		this.oldestDate = oldestDate;
	}
	public int getPlayCount() {
		return playCount;
	}
	public void setPlayCount(int playCount) {
		this.playCount = playCount;
	}
	
	public void increment(Date date) {
		// increment the counter
		playCount++;
		
		// then update the dates
		if(date.after(newestDate)) {
			newestDate = date;
		}
		if(date.before(oldestDate)) {
			oldestDate = date;
		}		
	}
}
