package com.sinaCrawler;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


public class SinaWeibo {
    private HttpClient client;
    private String username;    //��¼�ʺ�(����)
    private String password;    //��¼����(����)
    private String su;          //��¼�ʺ�(Base64����)
    private String sp;          //��¼����(���ֲ���RSA���ܺ������)
    private long servertime;    //��ʼ��¼ʱ�����������ص�ʱ���,������������Լ���¼��
    private String nonce;       //��ʼ��¼ʱ�����������ص�һ���ַ�������������Լ���¼��
    private String rsakv;       //��ʼ��¼ʱ�����������ص�һ���ַ�������������Լ���¼��
    private String pubkey;      //��ʼ��¼ʱ�����������ص�RSA��Կ

    private String errInfo;     //��¼ʧ��ʱ�Ĵ�����Ϣ
    private String location;    //��¼�ɹ������ת����

    private String url;

    public SinaWeibo(String username, String password) {
        client = new DefaultHttpClient();
        this.username = username;
        this.password = password;
    }


    /**
     * ��ʼ��¼��Ϣ&lt;br&gt;
     * ����false˵����ʼʧ��
     * @return
     */
    public boolean preLogin() {
        boolean flag = false;
        try {
            su = new String(Base64.encodeBase64(URLEncoder.encode(username, "UTF-8").getBytes()));
            String url = "http://login.sina.com.cn/sso/prelogin.php?entry=weibo&rsakt=mod&checkpin=1&" +
                         "client=ssologin.js(v1.4.5)&_=" + getTimestamp();
            url += "&su=" + su;
            String content;
            content = HttpTools.getRequest(client, url);
            //System.out.println(content);
            System.out.println("content------------" + content);
            JSONObject json = JSONObject.fromObject(content);
            System.out.println(json);
            servertime = json.getLong("servertime");
            nonce = json.getString("nonce");
            rsakv = json.getString("rsakv");
            pubkey = json.getString("pubkey");
            flag = encodePwd();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
        return flag;
    }

    /**
     * ��¼
     * @return true:��¼�ɹ�
     */
    public boolean login() {
        if(preLogin()) {
            String url = "http://login.sina.com.cn/sso/login.php?client=ssologin.js(v1.4.5)";
            List<NameValuePair> parms = new ArrayList<NameValuePair>();
            parms.add(new BasicNameValuePair("entry", "weibo"));
            parms.add(new BasicNameValuePair("geteway", "1"));
            parms.add(new BasicNameValuePair("from", ""));
            parms.add(new BasicNameValuePair("savestate", "7"));
            parms.add(new BasicNameValuePair("useticket", "1"));
            parms.add(new BasicNameValuePair("pagerefer", "http://login.sina.com.cn/sso/logout.php?entry=miniblog&r=http%3A%2F%2Fweibo.com%2Flogout.php%3Fbackurl%3D%2F"));
            parms.add(new BasicNameValuePair("vsnf", "1"));
            parms.add(new BasicNameValuePair("su", su));
            parms.add(new BasicNameValuePair("service", "miniblog"));
            parms.add(new BasicNameValuePair("servertime", servertime + ""));
            parms.add(new BasicNameValuePair("nonce", nonce));
            parms.add(new BasicNameValuePair("pwencode", "rsa2"));
            parms.add(new BasicNameValuePair("rsakv", rsakv));
            parms.add(new BasicNameValuePair("sp", sp));
            parms.add(new BasicNameValuePair("encoding", "UTF-8"));
            parms.add(new BasicNameValuePair("prelt", "182"));
            parms.add(new BasicNameValuePair("url", "http://weibo.com/ajaxlogin.php?framelogin=1&callback=parent.sinaSSOController.feedBackUrlCallBack"));
            parms.add(new BasicNameValuePair("returntype", "META"));
            try {
                String content = HttpTools.postRequest(client, url, parms);
                System.out.println("content----------" + content);
                String regex = "location\\.replace\\(\"(.+?)\"\\);";
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(content);
                if(m.find()) {
                    location = m.group(1);
                    if(location.contains("reason=")) {
                        errInfo = location.substring(location.indexOf("reason=") + 7);
                        errInfo = URLDecoder.decode(errInfo, "GBK");
                    } else {
                        String result = HttpTools.getRequest(client, location);
                        System.out.println("result--------------" + result);
                        return true;
                    }
                }
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //          url = "http://www.weibo.com/hm";
            //          System.out.println(MyUrlUtil.getResource(url));
        }
        return false;
    }

    /**
     * �������RSA����&lt;br&gt;
     * ����false˵������ʧ��
     * @return
     */
    private boolean encodePwd() {
        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine se = sem.getEngineByName("javascript");
        try {
            FileReader fr = new FileReader("E:\\encoder.js");
            se.eval(fr);
            Invocable invocableEngine = (Invocable) se;
            String callbackvalue = (String) invocableEngine.invokeFunction("encodePwd", pubkey, servertime, nonce, password);
            sp = callbackvalue;
            return true;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("���ܽű�encoder.sjδ�ҵ�");
        } catch (ScriptException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
        errInfo = "�������ʧ��!";
        return false;
    }

    public String getErrInfo() {
        return errInfo;
    }

    /**
     * ��ȡʱ���
     * @return
     */
    private long getTimestamp() {
        Date now = new Date();
        return now.getTime();
    }

    public static void main(String[] args) throws ClientProtocolException, IOException {
        SinaWeibo weibo = new SinaWeibo("15527244406", "jack!=2594147");
        if(weibo.login()) {
            System.out.println("登陆成功");
            String url = "http://www.weibo.com/hm";
            //          String source = MyUrlUtil.getResource(url);
            //          System.out.println(source);
        } else {
            System.out.println("登陆失败");
        }
    }
}