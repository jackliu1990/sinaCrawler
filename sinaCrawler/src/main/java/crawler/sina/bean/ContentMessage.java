package crawler.sina.bean;

public class ContentMessage {
	private String uid;
	private String shareContent;
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
	
	public void setPubDate(String pubDate){
		this.pubDate=pubDate;
	}
	
	public String getPubDate(){
		return this.pubDate;
	}
	
}
