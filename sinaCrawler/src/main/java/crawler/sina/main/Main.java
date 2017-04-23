package crawler.sina.main;

import java.io.IOException;
import java.util.List;

import jdbc.PostGreSql.control.JdbcConnection;

import org.apache.http.client.ClientProtocolException;

import crawler.sina.bean.SinaUserInfo;
import crawler.sina.craw.CrawSina;
import crawler.sina.dispatch.BangDispatch;
import crawler.sina.login.Constant;
import crawler.sina.login.LoginSina;

public class Main {

	/**
	 * @author whp
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		loginSina();
		//BangDispatch.bangUserMessage("3977723914",50);
		//getMusicMessage("5150801187","1");
		getAllMusicShare();
		//getAllContentShare();
	}
	
	public static void getMusicMessage(String uid,String page) throws IOException{
		CrawSina crawSina = new CrawSina();
		BangDispatch.bangMusicMessage(uid,Integer.valueOf(page));
	} 
	
	public static void loginSina(){
		LoginSina ls = new LoginSina(Constant.weiboUsername, Constant.weiboPassword);
		ls.dologinSina();
	}
	
	
	public static void GetFansList(){
		long startTime = System.currentTimeMillis();
		LoginSina ls = new LoginSina(Constant.weiboUsername, Constant.weiboPassword);
		ls.dologinSina();
		BangDispatch.bangUserMessage("3977723914",1);
		//CrawSina crawSina = new CrawSina();
		//crawSina.getUserInfo("jackliu1990");
		//crawSina.getFansListByUid("3977723914", "1");
		long endTime = System.currentTimeMillis();
		long useTime = endTime - startTime;
		System.out.println("共用时:" + useTime);
	}
	
	public static void getAllMusicShare(){
		JdbcConnection conn = new JdbcConnection();
		List<SinaUserInfo> usrlist=conn.getUserList();
		for(int i=0;i<usrlist.size();i++){
			SinaUserInfo userInfo = usrlist.get(i);
			BangDispatch.bangMusicMessage(userInfo.getUid(),1);
		}
	}
	
	public static void getAllContentShare(){
		JdbcConnection conn = new JdbcConnection();
		List<SinaUserInfo> usrlist=conn.getUserList();
		for(int i=0;i<usrlist.size();i++){
			SinaUserInfo userInfo = usrlist.get(i);
			BangDispatch.bangContentMessage(userInfo.getUid(),1);
		}
	}

}
