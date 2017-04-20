package crawler.sina.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class ParserFansList {

	/**
	 * @author whp
	 * @param text 包含需要解析的粉丝列表的正文
	 * @return 返回解析得到的粉丝列表
	 */
	public static List<String> getFansList(String text){
		Pattern p = Pattern.compile("\\<script>FM.view(.*?)\\</script>");
		 Matcher m = p.matcher(text);
		 List<String> rsList=new ArrayList<String>();
		 while(m.find()){
			 String t_rs=m.group(1);
			 if(t_rs.contains("html") && t_rs.contains("follow_list")){
			 rsList.add(t_rs);
			 }
		 }
		 
		 if(rsList.isEmpty()){
			 System.out.println("抓取异常!!!!");
			 }
		 String topics = rsList.get(0);
		// System.out.println(topics);
		p = Pattern.compile("\\<li class\\=(.*?)li>");
		m = p.matcher(topics);
		 List<String> ilList=new ArrayList<String>();
		while(m.find()){
		if(m.group(1).startsWith("\\\"follow_item S_line2\\\"")){
		   String li=m.group(1);
		   ilList.add(li);
		   //<img usercard=\"id=5395580472&refer_flag=1005050005_\" width=\"50\" height=\"50\" alt=\"锶慧hongdan\" 
		   Pattern puid = Pattern.compile("\\<img usercard\\=\\\\\"id=(.*?)\\&refer_flag");
		   Matcher muid = puid.matcher(li);
		   while(muid.find()){
		    String uid=muid.group(1);
		    System.out.println("uid:"+uid);
		   }
		   //class=\"S_txt1\">锶慧hongdan<\/a>
		   Pattern pnick= Pattern.compile("class\\=\\\\\"S\\_txt1\\\\\">(.*?)\\<\\\\/a>");
		   Matcher mnick = pnick.matcher(li);
		   while(mnick.find()){
			    String nick=mnick.group(1);
			    System.out.println("昵称:"+nick);
			   }   
		}
		}
		
		
		if(text.indexOf("follow_list")>0){
			System.out.println("OK");
		}
		if(text.indexOf("\\\"follow_list\\\"")>0){
			System.out.println("OK");
		}
		if(text.indexOf("\\follow_list\\")>0){
			System.out.println("OK");
		}
		if(text.indexOf("class=\"follow_list\"")>0){
			System.out.println("OK");
		}
		if(text.indexOf("\\<ul class=\"follow_list\">")>0){
			System.out.println("OK");
		}
		List<String> fansList = new ArrayList<String>();
		String str = "";
		String regex = "<ul class=\"follow_list\">";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
   		while (matcher.find()) {
   			str = matcher.group(2);
   		}
   		str = str.replaceAll("\\\\t|\\\\r|\\\\n|\\\\", "");
   		Parser fansListParser  = new Parser();
   		try {
   			fansListParser.setInputHTML("<html><body>"+topics+"</body></html>");
   			NodeFilter filter = new HasAttributeFilter("class","follow_item S_line2");
			NodeList list = fansListParser.extractAllNodesThatMatch(filter);
			for(int i=0; i<list.size();i++){
                Node node = (Node)list.elementAt(i);
                String fan = node.getText();
                String regexFan = "uid=(.*)&fnick";
                Pattern patternFan = Pattern.compile(regexFan);
                Matcher matcherFan = patternFan.matcher(fan);
           		while (matcherFan.find()) {
           			fan = matcherFan.group(1);
           		}
           		fansList.add(fan);
            }
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fansList;
	}
}
