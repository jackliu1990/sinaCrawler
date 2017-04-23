package crawler.sina.bean;

import java.sql.Date;

public class MusicMessage {
	private String uid;
	private String shareContent;
	private String singer;
	private String songName;
	private String pubDate;
	
	public void setUid(String uid){
		this.uid=uid;
	}
	
	public String getUid(){
		return this.uid;
	}
	
	public void setShareContent(String shareContent){
		this.shareContent=shareContent;
	}
	
	public String getShareContent(){
		return this.shareContent;
	}
	
	
	public void setSinger(String singer){
		this.singer=singer;
	}
	
	public String getSinger(){
		return this.singer;
	}
	
	public void setSongName(String songName){
		this.songName=songName;
	}
	
	public String getSongName(){
		return this.songName;
	}
	
	public void setPubDate(String pubDate){
		this.pubDate=pubDate;
	}
	
	public String getPubDate(){
		return this.pubDate;
	}
	
}
