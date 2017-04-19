package com.sinaCrawler;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.gargoylesoftware.htmlunit.WebClient;

public class seleniumCrawler{
	
	
	public static void main( String[] args )
    {
		example();
    }
	
	private static void example()
	{
	//for chrome 
	System.setProperty("webdriver.chrome.driver","E:/sinaCrawler/chromedriver_win32/chromedriver.exe");
	WebDriver driver =new ChromeDriver();


	WebDriverWait w =new WebDriverWait(driver,10);
	driver.get("http://www.baidu.com/");  
    w.until(ExpectedConditions.visibilityOfElementLocated(By.id("lg")));
	w.until(ExpectedConditions.elementToBeClickable(By.id("u1")));

	// Find the text input element by its name
	//WebElement element = driver.findElement(By.id("kw1"));
	//element.sendKeys("liaoxiangui");
	//element.submit();  
    WebElement a=driver.findElement(By.id("lg"));
    System.out.println(a.getText());
	System.out.println("Page title is: "+ driver.getTitle());

	// baidu's search is rendered dynamically with JavaScript.
	// Wait for the page to load, timeout after 10 seconds
	/*w.until(new ExpectedCondition<Boolean>(){
		public Boolean apply(WebDriver d){
			return d.getTitle().toLowerCase().startsWith("liaoxiangui");
			}
	});

	System.out.println("Page title is: "+ driver.getTitle());*/
	
	WebDriver dr = new HtmlUnitDriver();
	dr.get("https://s.taobao.com/search?spm=a21bo.50862.201856-fline.1.vtCVNa&q=%E8%BF%9E%E8%A1%A3%E8%A3%99&refpid=420460_1006&source=tbsy&style=grid&tab=all&pvid=d0f2ec2810bcec0d5a16d5283ce59f66");
	

	  WebElement webElement = dr.findElement(By.xpath("/html"));
      System.out.println(webElement.getAttribute("outerHTML"));

	try{Thread.sleep(10000);}catch(Exception e){}
	        driver.quit();
	}
}

