package crawler.sina.dispatch;

import java.io.IOException;
import java.util.List;

import jdbc.PostGreSql.control.JdbcConnection;

import org.apache.http.client.ClientProtocolException;

import crawler.sina.bean.ContentMessage;
import crawler.sina.bean.MusicMessage;
import crawler.sina.bean.SinaUserInfo;
import crawler.sina.craw.CrawSina;

public class BangDispatch {
	
	/**
	 * 获取微博用户粉丝信息并将其插入数据库
	 * @param uid
	 * @param PageTotal
	 */
	public static void bangUserMessage(String uid,int PageTotal){
		CrawSina crawSina =new CrawSina();
		JdbcConnection jdbcConnection = new JdbcConnection();
		try{
			for(int pageNum=1; pageNum<=PageTotal; pageNum++)
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
	
	/**
	 * 获取每个人的微博音乐信息，并将其插入数据库
	 * @param uid 用户uid
	 * @param PageTotal 抓取总页数
	 */
	public static void bangMusicMessage(String uid,int PageTotal){
		CrawSina crawSina =new CrawSina();
		JdbcConnection jdbcConnection = new JdbcConnection();
		try{
			for(int pageNum=1; pageNum<=PageTotal; pageNum++)
			{
			   List<MusicMessage> MusicMessList = crawSina.getMusicMessage(uid, String.valueOf(pageNum));
			   jdbcConnection.userMusicMessage(uid, MusicMessList);
			}
		}catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void bangContentMessage(String uid,int PageTotal){
		CrawSina crawSina =new CrawSina();
		JdbcConnection jdbcConnection = new JdbcConnection();
		try{
			for(int pageNum=1; pageNum<=PageTotal; pageNum++)
			{
			   List<ContentMessage> contentList = crawSina.getContentMesage(uid, String.valueOf(pageNum));
			   jdbcConnection.userContentMessage(uid, contentList);
			}
		}catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
