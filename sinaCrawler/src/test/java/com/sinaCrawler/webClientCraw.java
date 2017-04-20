package com.sinaCrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;  
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

public class webClientCraw {
    public static void getHtml(){
    	String path = "https://s.taobao.com/search?spm=a21bo.50862.201856-fline.1.vtCVNa&q=%E8%BF%9E%E8%A1%A3%E8%A3%99&refpid=420460_1006&source=tbsy&style=grid&tab=all&pvid=d0f2ec2810bcec0d5a16d5283ce59f66"; 
    	WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setUseInsecureSSL(true);//支持https
        webClient.getOptions().setJavaScriptEnabled(true); // 启用JS解释器，默认为true
        webClient.getOptions().setCssEnabled(false); // 禁用css支持
        webClient.getOptions().setThrowExceptionOnScriptError(false); // js运行错误时，是否抛出异常
        webClient.getOptions().setTimeout(10000); // 设置连接超时时间 ，这里是10S。如果为0，则无限期等待
        webClient.getOptions().setDoNotTrackEnabled(false);
        webClient.setJavaScriptTimeout(8000);//设置js运行超时时间
        webClient.waitForBackgroundJavaScript(500);//设置页面等待js响应时间,
        
        HtmlPage page = null;
		try {
			page = webClient.getPage(path);
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        System.out.println(page.asXml());
        
        /*try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        HtmlDivision div=(HtmlDivision)page.getElementById("forecast");
        String xml=div.asXml();
        if(xml.indexOf("forecast-data-loading")>=0)
        {
            System.out.println("htmlUnit解析页面失败");
        }
        else
        {
            System.out.println("htmlUnit解析页面成功");
            int[] aqis=new int[8];
            
            int i=0;
            List<HtmlTable> tables=(List<HtmlTable>)div.getByXPath("./div[2]/center[1]/table");
            if(tables.size()==8)
            {
                for(HtmlTable table : tables)
                {  
                    List<HtmlTableRow> trs=(List<HtmlTableRow>)table.getByXPath("./tbody/tr[4]");
                    HtmlTableRow tr=trs.get(0);
                    
                    int aqi=0;
                    List<HtmlTableCell> cells = (List<HtmlTableCell>)tr.getByXPath("./td");
                    for(HtmlTableCell cell : cells)
                    {
                        String s=cell.asText();
                        String [] values=s.split("\r\n");
                        aqi=aqi+(Integer.parseInt(values[0])+Integer.parseInt(values[1]))/2 ;
                    }
                    aqi=aqi/cells.size();
                    aqis[i]=aqi;
                    i=i+1;
                }
            }
    }*/
    }
    
    public static void main( String[] args ) throws FailingHttpStatusCodeException, MalformedURLException, IOException
    {
    	//getHtml();
        WebClient client = new WebClient(BrowserVersion.CHROME);
        client.getOptions().setJavaScriptEnabled(true);    //默认执行js，如果不执行js，则可能会登录失败，因为用户名密码框需要js来绘制。
        client.getOptions().setCssEnabled(false);
        client.setAjaxController(new NicelyResynchronizingAjaxController());
        client.getOptions().setThrowExceptionOnScriptError(false);        

        HtmlPage page = client.getPage("http://login.sina.com.cn/sso/login.php?client=ssologin.js(v1.3.16)");
        //System.out.println(page.asText());
        //登录
        HtmlInput ln = page.getHtmlElementById("username");
        HtmlInput pwd = page.getHtmlElementById("password");
        //HtmlInput btn = page.getFirstByXPath(".//*[@id='vForm']/div[1]/div[0]/ul/li[6]/div[0]/input");
        DomNodeList<DomElement> btn = page.getElementsByTagName("input");
        ln.setAttribute("value", "15527244406");
        pwd.setAttribute("value", "jack!=2594147");
        HtmlPage page2 = btn.get(btn.getLength()-1).click();
        //登录完成，现在可以爬取任意你想要的页面了。
        System.out.println("\n\n\n");
        //System.out.println(page2.asText());

        HtmlPage page3 = client.getPage("http://weibo.com/3977723914/fans?rightmod=1&wvr=6");
        try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(" : " + page3.asXml());
        final HtmlDivision div = page3.getHtmlElementById("plc_main");  
      //获取子元素  
        /*Iterator<DomElement> iter = div.getChildElements().iterator();  
        while(iter.hasNext()){  
            System.out.println(iter.next().getTextContent());  
        }  
        //获取所有输出链接  
        for(HtmlAnchor anchor : page3.getAnchors()){  
            System.out.println(anchor.getTextContent()+" : "+anchor.getAttribute("href"));  
        } */
        client.close();
    }
}
