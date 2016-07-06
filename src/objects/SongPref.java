package objects;

import java.util.Date;

public class SongPref {
	private String songName;
	private Date playDate;
	private int plus;
	private int minus;
	private int upvote;
	private int downvote;
	private int playCount;
	private String tag;
	
	public int getIdMod() {
		int mod = plus + minus;
		
		if(mod>0) {
			if(upvote==1) {
				mod = mod*2;
			} else if (downvote==1) {
				mod = mod/2;
			}
		} else if(mod<0) {
			if(upvote==1) {
				mod = mod/2;
			} else if (downvote==1) {
				mod = mod*2;
			}
		}
		
		return mod;
	}
	
	public String getSongName() {
		return songName;
	}
	public void setSongName(String songName) {
		this.songName = songName;
	}
	public Date getPlayDate() {
		return playDate;
	}
	public void setPlayDate(Date playDate) {
		this.playDate = playDate;
	}
	public int getPlus() {
		return plus;
	}
	public void setPlus(int plus) {
		this.plus = plus;
	}
	public int getMinus() {
		return minus;
	}
	public void setMinus(int minus) {
		this.minus = minus;
	}
	public int getUpvote() {
		return upvote;
	}
	public void setUpvote(int upvote) {
		this.upvote = upvote;
	}
	public int getDownvote() {
		return downvote;
	}
	public void setDownvote(int downvote) {
		this.downvote = downvote;
	}
	public int getPlayCount() {
		return playCount;
	}
	public void setPlayCount(int playCount) {
		this.playCount = playCount;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
}
