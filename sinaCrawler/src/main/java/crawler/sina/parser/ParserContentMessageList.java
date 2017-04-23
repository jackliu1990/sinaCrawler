package crawler.sina.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import crawler.sina.bean.ContentMessage;
import crawler.sina.utils.replaceSpatialCode;

public class ParserContentMessageList {
	public static List<ContentMessage> getContentMessageList(String text) {
		List<ContentMessage> ContentMessageList = new ArrayList<ContentMessage>();
		
		Pattern textp = Pattern.compile("\\<div class\\=\\\\\"WB\\_text\\ W\\_f14\\\\\"(.*?)\\<\\\\/div>");
		Matcher textm = textp.matcher(text);
		while(textm.find()){
			ContentMessage contentMessage = new ContentMessage();
			String contentMes = textm.group(1);
			String regEx="([\u4e00-\u9fa5]+)";  
			Matcher matcher = Pattern.compile(regEx).matcher(contentMes);
		    StringBuffer shareContent = new StringBuffer();
			while(matcher.find()){
				shareContent.append(matcher.group());
			}
			contentMessage.setShareContent(shareContent.toString());
			ContentMessageList.add(contentMessage);
		}
		
		//mod=weibotime\" title=\"2017-04-22 00:14\" date
		Pattern timep = Pattern.compile("mod\\=weibotime\\\\\"\\ title\\=\\\\\"(.*?)\\\\\"\\ date");
		Matcher timem = timep.matcher(text);
		for(int i=0;i<ContentMessageList.size();i++){
			if(timem.find()){
				String TimeMes = timem.group(1);
				ContentMessageList.get(i).setPubDate(TimeMes);
			}
		}
		return ContentMessageList;
	}
}
