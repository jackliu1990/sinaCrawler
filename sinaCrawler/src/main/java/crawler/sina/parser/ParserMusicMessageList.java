package crawler.sina.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import crawler.sina.bean.MusicMessage;
import crawler.sina.utils.replaceSpatialCode;

public class ParserMusicMessageList {
	
	public static List<MusicMessage> getMusicMessageList(String text) {
		List<MusicMessage> MusicMessageList = new ArrayList<MusicMessage>();
		Pattern textp = Pattern.compile("\\<div class\\=\\\\\"WB\\_text\\ W\\_f14\\\\\"(.*?)\\<\\\\/div>");
		Matcher textm = textp.matcher(text);
		while(textm.find()){
			MusicMessage musicMessage = new MusicMessage();
			String musicMes = textm.group(1);
			String regEx="([\u4e00-\u9fa5]+)";  
			Matcher matcher = Pattern.compile(regEx).matcher(musicMes);
		    StringBuffer shareContent = new StringBuffer();
			while(matcher.find()){
				shareContent.append(matcher.group());
			}
			musicMessage.setShareContent(shareContent.toString());
			//usercard=\"name=陳綺貞cheerego\">@陳綺貞cheerego<\/a>《我喜欢上你时的内心活动》 <a 
			Pattern singSongPatter = Pattern.compile("usercard\\=\\\\\"name\\=(.*?)\\\\\"\\>(.*?)\\<\\\\/a>(.*?)\\<a");
			Matcher singSongMatch = singSongPatter.matcher(musicMes);
			if(singSongMatch.find()){
				String singer=singSongMatch.group(1);
				musicMessage.setSinger(singer);
				String songer=singSongMatch.group(3);
				musicMessage.setSongName(songer);;
			}
			MusicMessageList.add(musicMessage);
		}
		
		//mod=weibotime\" title=\"2017-04-22 00:14\" date
		Pattern timep = Pattern.compile("mod\\=weibotime\\\\\"\\ title\\=\\\\\"(.*?)\\\\\"\\ date");
		Matcher timem = timep.matcher(text);
		for(int i=0;i<MusicMessageList.size();i++){
			if(timem.find()){
				String TimeMes = timem.group(1);
				MusicMessageList.get(i).setPubDate(TimeMes);
			}
		}
		return MusicMessageList;
	}
	
	public static int GetMusicMessCount(String text){
		int page=0;
		return page;
	}
	
}
