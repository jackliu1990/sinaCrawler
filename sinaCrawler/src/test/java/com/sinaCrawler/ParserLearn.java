package com.sinaCrawler;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ParserLearn {
	  public static void main( String[] args ){
         String b ="<ul class=\"follow_list\">asasaasasasaasasasasas</ul>";
         
         String reg="<ul class=\"follow_list\">(.*)</ul>";
         List<String> c =getTeacherList(reg,b);
         
         
          String a ="教师10(0010)";
          List<String> test= getTeacherList("(?<=\\()(.+?)(?=\\))",a);
          System.out.println(test);
	  }
	  
	  public static List<String> getTeacherList(String reg,String managers){
	        List<String> ls=new ArrayList<String>();
	        Pattern pattern = Pattern.compile(reg);
	        Matcher matcher = pattern.matcher(managers);
	        while(matcher.find())
	            ls.add(matcher.group());
	        return ls;
	    }

}
