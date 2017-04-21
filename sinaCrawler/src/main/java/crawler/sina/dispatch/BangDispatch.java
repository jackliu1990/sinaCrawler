package crawler.sina.dispatch;

import java.io.IOException;
import java.util.List;

import jdbc.PostGreSql.control.JdbcConnection;

import org.apache.http.client.ClientProtocolException;

import crawler.sina.bean.SinaUserInfo;
import crawler.sina.craw.CrawSina;

public class BangDispatch {
	/**
	 * 
	 * @param uid 获取用户的微博信息和粉丝信息
	 */
	public static void bangUserMessage(String uid){
		CrawSina crawSina =new CrawSina();
		JdbcConnection jdbcConnection = new JdbcConnection();
		try{
			for(int pageNum=1; pageNum<=5; pageNum++)
			{
			   List<SinaUserInfo> fansUidList = crawSina.getFansListByUid(uid, String.valueOf(pageNum));
			   jdbcConnection.userFansList(uid, fansUidList);
			}
		}catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
