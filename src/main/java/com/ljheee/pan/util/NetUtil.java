package com.ljheee.pan.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

/**
 * Created by lijianhua04 on 2019/1/14.
 */
public class NetUtil {


    /**
     * 检查 代理有效性
     *
     * @param ip
     * @param port
     * @return
     */
    public static boolean checkProxy(String ip, int port) {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
        return doCheckProxy(proxy);
    }

    public static boolean checkProxy(Proxy proxy) {
        return doCheckProxy(proxy);
    }

    private static boolean doCheckProxy(Proxy proxy) {
        try {
            URL url = new URL("https://www.baidu.com");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection(proxy);
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            //连接建立超时时间还有读取数据超时时间，
            urlConnection.setConnectTimeout(6000);
            urlConnection.setReadTimeout(6000);
            long begin = System.currentTimeMillis();
            urlConnection.connect();
            if (urlConnection.usingProxy() && urlConnection.getResponseCode() == 200) {
                //代理 是存活的
                long end = System.currentTimeMillis();
                if (end - begin < 5000) {
                    return true;
                }
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }


}
