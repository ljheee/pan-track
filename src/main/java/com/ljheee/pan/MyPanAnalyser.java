package com.ljheee.pan;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

/**
 * 百度网盘 密码验证
 */
public class MyPanAnalyser {
    String cookie;
    private String ip = "";
    String info;

    // 使用代理，防止自己IP被封
    Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("218.241.219.226", 9999));


    // 先模拟浏览器执行一次访问，获取cookie
    public void doHttpGet(String urlString) throws IOException {
        // 新建url连接
        URL url = new URL(urlString);
        info = urlString.substring(urlString.indexOf('?') + 1);

        // 打开链接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy);
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

    public boolean tryPWD(String pwd) throws UnsupportedEncodingException,
            IOException {
        String data = HttpPost("pwd=" + pwd + "&vcode=&vcode_str=");
        System.out.println("returnData=" + data);


        if (data.contains("\"errno\":-9"))//提取码错误
            return false;
        else if (data.contains("\"errno\":0"))
            return true;
        else
            return false;
    }

    /**
     * 使用密码进行POST请求
     *
     * @param param
     * @return
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    public String HttpPost(String param) throws UnsupportedEncodingException,
            IOException {
        System.out.println(info);
        // 新建url连接
        String verifyUrl = "http://pan.baidu.com/share/verify?"
                + info
                + "&t="
                + System.currentTimeMillis()
                + "&bdstoken=null&channel=chunlei&clienttype=0&web=1&app_id=033646&logid=MTUwMTEyNDM2OTY5MzAuOTE5NTU5NjQwMTk0NDM0OA==";

        System.out.println(verifyUrl);
        URL url = new URL(verifyUrl);
        // 打开链接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy);
        // 设置参数
        connection.setDoOutput(true);
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

        System.out.println("cookie=" + cookie);
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
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {// 循环读取流
            sb.append(line);
        }
        br.close();// 关闭流
        return sb.toString();
    }

    public static void main(String[] args) {


        // 只能是
        String urlString = "https://pan.baidu.com/share/init?surl=4rGxYe_W3oEFsRt9nS0wdg";


        MyPanAnalyser myPanAnalyser = new MyPanAnalyser();
        try {
            myPanAnalyser.doHttpGet(urlString);
            boolean flag = myPanAnalyser.tryPWD("92wg");
            if (flag) {
                System.out.println("ok");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
