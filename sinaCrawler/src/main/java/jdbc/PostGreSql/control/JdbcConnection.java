package jdbc.PostGreSql.control;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import crawler.sina.bean.ContentMessage;
import crawler.sina.bean.MusicMessage;
import crawler.sina.bean.SinaUserInfo;
import net.sf.json.JSONArray;

public class JdbcConnection {
	public static int infoInsertNum = 0;
	public static int infoUpdateNum = 0;

	public Connection getConnection() {
		Connection conn = null;
		String url = "jdbc:postgresql://127.0.0.1:5432/postgres";
		String user = "postgres";
		String password = "123456";
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 将用户uid与数据Map=>json 存入userInfo表中
	 * 
	 * @author whp
	 * @param uid
	 *            用户uid 对应数据库userInfo表中uid字段
	 * @param json
	 *            用户数据，转为json数据 对应数据库userInfo表中userInfoJson字段
	 */
	public void userInfo(String uid, String json) {
		try {
			Connection conn = getConnection();
			Statement stmt = conn.createStatement();
			String sqlSelect = "select * from userInfo where uid = '" + uid
					+ "'";
			ResultSet result = stmt.executeQuery(sqlSelect);
			if (result.next()) {
				String sqlUpdate = "UPDATE userInfo SET userInfoJson = '"
						+ json + "' WHERE uid = '" + uid + "'";
				stmt.executeUpdate(sqlUpdate);
				System.out.println("update" + infoUpdateNum++);
			} else {
				String sqlInsert = "INSERT INTO userInfo (uid, userInfoJson) VALUES ('"
						+ uid + "','" + json + "')";
				stmt.executeUpdate(sqlInsert);
				System.out.println("insert" + infoInsertNum++);
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out
					.println("error emoj字符问题====================================\n"
							+ "uid:" + uid + "   " + json);
		}
	}

	/**
	 * 将用户的粉丝信息插入数据库
	 * 
	 * @param uid
	 *            用户的uid
	 * @param fansList
	 *            用户的粉丝几何
	 */
	public void userFansList(String uid, List<SinaUserInfo> fansList) {
		Connection conn = getConnection();
		try {
			Statement stmt = conn.createStatement();
			for (int i = 0; i < fansList.size(); i++) {
				SinaUserInfo userinfo = fansList.get(i);
				String sqlInsert = "INSERT INTO userinfo (uid, nick) VALUES ("
						+ Long.valueOf(userinfo.getUid()) + ",'"
						+ userinfo.getNick() + "')";
				stmt.executeUpdate(sqlInsert);
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param uid
	 *            用户的uid
	 * @param musicList
	 *            用户分享的音乐信息
	 */
	public void userMusicMessage(String uid, List<MusicMessage> musicList) {
		Connection conn = getConnection();
		try {
			Statement stmt = conn.createStatement();
			for (int i = 0; i < musicList.size(); i++) {
				MusicMessage musicinfo = musicList.get(i);
				String sqlInsert = "INSERT INTO musicMessage (uid, shareContent,singer,songName,pubDate) VALUES ("
						+ Long.valueOf(uid)
						+ ",'"
						+ musicinfo.getShareContent()
						+ "','"
						+ musicinfo.getSinger()
						+ "','"
						+ musicinfo.getSongName()
						+ "','"
						+ musicinfo.getPubDate() + "')";
				stmt.executeUpdate(sqlInsert);
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void userContentMessage(String uid, List<ContentMessage> contentList) {
		Connection conn = getConnection();
		try {
			Statement stmt = conn.createStatement();
			for (int i = 0; i < contentList.size(); i++) {
				ContentMessage contentinfo = contentList.get(i);
				String sqlInsert = "INSERT INTO contentMessage (uid, shareContent,pubDate) VALUES ("
						+ Long.valueOf(uid)
						+ ",'"
						+ contentinfo.getShareContent()
						+ "','"
						+ contentinfo.getPubDate() + "')";
				stmt.executeUpdate(sqlInsert);
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public List<SinaUserInfo> getUserList(){
		Connection conn = getConnection();
		List<SinaUserInfo> uselist = new ArrayList<SinaUserInfo>();
		try {
			Statement stmt = conn.createStatement();
		    String sqlSelect="select * from userinfo";
			ResultSet rs=stmt.executeQuery(sqlSelect);
			while(rs.next()){
				SinaUserInfo userInfo = new SinaUserInfo();
				userInfo.setUid(rs.getObject("uid").toString());
				userInfo.setNick(rs.getObject("nick").toString());
				uselist.add(userInfo);
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return uselist;
	}
}
