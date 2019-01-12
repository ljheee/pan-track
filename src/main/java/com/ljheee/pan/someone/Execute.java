package com.ljheee.pan.someone;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * https://blog.csdn.net/qq_32016151/article/details/77980730
 */
public class Execute {

    String n = "";

    /**
     * 执行多线程任务
     *
     * @param sum
     */
    public void executeThread(String urlString, int sum) {
        List<String> pwd = getPWD();
        int everyThread = pwd.size() / sum;
        for (int i = 0; i < sum; i++) {
            n = i + "";
            ThreadTry n = new ThreadTry(urlString, i * everyThread, (i + 1)
                    * everyThread, pwd);
            n.start();
        }
    }

    /**
     * 获取所有密码
     *
     * @return
     */
    private List<String> getPWD() {
        char s[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
                'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'g', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
        List<String> list = new ArrayList<String>();
        for (char one : s) {
            for (char two : s) {
                for (char three : s) {
                    for (char four : s) {
                        list.add("" + one + two + three + four);
                    }
                }
            }
        }
        return list;
    }


    public static void main(String[] args) throws IOException {


        new Execute().executeThread("https://pan.baidu.com/share/init?surl=4rGxYe_W3oEFsRt9nS0wdg", 1000);
    }
}