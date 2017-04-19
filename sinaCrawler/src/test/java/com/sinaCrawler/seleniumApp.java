package com.sinaCrawler;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;

public class seleniumApp {

		public void testFirefox(){
		//如果Firefox不是安装在默认路径下，需要手动指定其位置
		System.setProperty("webdriver.firefox.bin",
		"C://Program Files//Mozilla Firefox//firefox.exe");
		//加载Firefox默认配置
		ProfilesIni pi = new ProfilesIni(); 
		FirefoxProfile profile = pi.getProfile("default");

		//启动Firefox浏览器
		WebDriver ffDriver = new FirefoxDriver(profile);
		Navigation navigation = ffDriver.navigate();	
		//打开360搜索
		navigation.to("http://www.so.com");

		//搜索HTML元素，支持按id、name、标签名、CSS选择器、xpath语法等方式
		//用id获取搜索输入框
		WebElement inputText = ffDriver.findElement(By.id("input")); 
		//用id获取搜索按钮
		WebElement submitButton = ffDriver.findElement(By.id("search-button")); 
		//搜索输入框内输入“喵帕斯”
		inputText.sendKeys("喵帕斯"); 
		//为演示点击效果，延迟3秒
		try {
		Thread.sleep(3000);
		} catch (InterruptedException e) {
		e.printStackTrace();
		}	
		//点击“搜一下”按钮
		submitButton.click(); 

		//使用xpath语法获取文章标题
		List<WebElement> titleList = ffDriver.findElements(By.xpath("//h3[@class='res-title']/a")); 
		//输出获取到的文章标题
		for(WebElement e : titleList)
		System.out.println(e.getText());

		//5秒后关闭浏览器
		try {
		Thread.sleep(5000);
		} catch (InterruptedException e) {
		e.printStackTrace();
		}
		if(ffDriver != null)
		ffDriver.close();
		}


		public static void main(String args[]){
			seleniumApp demo = new seleniumApp();
		   demo.testFirefox();
		}
}