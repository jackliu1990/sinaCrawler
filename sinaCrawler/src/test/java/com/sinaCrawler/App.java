package com.sinaCrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws FailingHttpStatusCodeException, MalformedURLException, IOException
    {
    	 final WebClient client = new WebClient(BrowserVersion.CHROME);  
          client.getOptions().setJavaScriptEnabled(true);    //默认执行js，如果不执行js，则可能会登录失败，因为用户名密码框需要js来绘制。
          client.getOptions().setCssEnabled(false);
          client.setAjaxController(new NicelyResynchronizingAjaxController());
          client.getOptions().setThrowExceptionOnScriptError(false);  
         final HtmlPage page = client.getPage("http://yangshangchuan.iteye.com");  
         final HtmlDivision div = page.getHtmlElementById("blog_actions");  
         //获取子元素  
         Iterator<DomElement> iter = div.getChildElements().iterator();  
         while(iter.hasNext()){  
             System.out.println(iter.next().getTextContent());  
         }  
         //获取所有输出链接  
         for(HtmlAnchor anchor : page.getAnchors()){  
             System.out.println(anchor.getTextContent()+" : "+anchor.getAttribute("href"));  
         }  
         client.close();  
    }
}
