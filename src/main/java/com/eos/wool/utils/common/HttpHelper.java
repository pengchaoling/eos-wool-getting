package com.eos.wool.utils.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class HttpHelper
{
    public static final String CHARSET_NONE = null;
    public static final String CHARSET_UTF8 = "utf-8";
    public static final String CHARSET_GBK = "gbk";
    public static final String URL_ENCODING_UTF8 = "utf-8";
    public static final String URL_ENCODING_GBK = "gbk";
    public static final int CONNECT_TIMEOUT_TIME = 3000;
    public static final int CONNECT_TIMEOUT_TIME_10000 = 10000;

    public static String sendGet(String url, String charset)
    {
        String result = "";
        BufferedReader in = null;
        try
        {
            URL realUrl = new URL(url);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            connection.setConnectTimeout(3000);
            String line;
            for (in = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset)); (line = in.readLine()) != null; result = result + line) {}
            return result;
        }
        catch (Exception var15)
        {
            var15.printStackTrace();
            System.out.println("请求异常" + url + " - " + var15.getMessage());
        }
        finally
        {
            try
            {
                if (in != null) {
                    in.close();
                }
            }
            catch (Exception var14)
            {
                var14.printStackTrace();
            }
        }
        return result;
    }

    public static String sendPostUrl(String url, String param, String charset)
    {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try
        {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(3000);
            out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();
            String line;
            for (in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset)); (line = in.readLine()) != null; result = result + line) {}
            return result;
        }
        catch (Exception var17)
        {
            System.out.println("请求异常" + var17);
            var17.printStackTrace();
        }
        finally
        {
            try
            {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            }
            catch (IOException var16)
            {
                var16.printStackTrace();
            }
        }
        return result;
    }

    public static String sendPost(String url, Map<String, String> param, String charset)
    {
        StringBuffer buffer = new StringBuffer();
        if ((param != null) && (!param.isEmpty())) {
            try
            {
                Iterator i$ = param.entrySet().iterator();
                while (i$.hasNext())
                {
                    Map.Entry<String, String> entry = (Map.Entry)i$.next();
                    buffer.append((String)entry.getKey()).append("=").append(URLEncoder.encode((String)entry.getValue(), "gbk")).append("&");
                }
            }
            catch (Exception var21)
            {
                var21.printStackTrace();
            }
        }
        buffer.deleteCharAt(buffer.length() - 1);
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try
        {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(3000);
            out = new PrintWriter(conn.getOutputStream());
            out.print(buffer);
            out.flush();
            String line;
            for (in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset)); (line = in.readLine()) != null; result = result + line) {}
            return result;
        }
        catch (Exception var19)
        {
            var19.printStackTrace();
        }
        finally
        {
            try
            {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            }
            catch (IOException var18)
            {
                var18.printStackTrace();
            }
        }
        return result;
    }

    public static String postJson(String url, String jsonparams, String charset)
    {
        try
        {
            URL object = new URL(url);
            HttpURLConnection con = (HttpURLConnection)object.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestMethod("POST");
            con.setConnectTimeout(3000);
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream(), charset);
            String json = jsonparams.toString().replace("\t", " ");
            wr.write(json);
            wr.flush();
            StringBuilder sb = new StringBuilder();
            int HttpResult = con.getResponseCode();
            if (HttpResult != 200)
            {
                System.out.println("请求异常" + con.getResponseMessage() + ",params=" + charset);
                return "";
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            return sb.toString();
        }
        catch (Exception var11)
        {
            var11.printStackTrace();
        }
        return "{}";
    }

    public static void main(String[] args) {}
}
