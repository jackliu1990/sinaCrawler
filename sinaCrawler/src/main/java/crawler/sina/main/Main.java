package crawler.sina.main;

import java.io.IOException;
import org.apache.http.client.ClientProtocolException;

import crawler.sina.craw.CrawSina;
import crawler.sina.dispatch.BangDispatch;
import crawler.sina.login.Constant;
import crawler.sina.login.LoginSina;

public class Main {

	/**
	 * @author whp
	 * @param args
	 */
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		LoginSina ls = new LoginSina(Constant.weiboUsername, Constant.weiboPassword);
		ls.dologinSina();
		CrawSina crawSina = new CrawSina();
		crawSina.getUserInfo("jackliu1990");
		long endTime = System.currentTimeMillis();
		long useTime = endTime - startTime;
		System.out.println("共用时:" + useTime);
	}

}
