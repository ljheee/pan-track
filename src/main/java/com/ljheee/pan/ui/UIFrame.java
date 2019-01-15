package com.ljheee.pan.ui;

import com.ljheee.pan.someone.Execute;
import com.ljheee.pan.util.NetUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * MyZip
 *
 * @author ljheee
 */
public class UIFrame {

    private JFrame jf = null;
    private JLabel leftInfo = new JLabel("状态栏:");
    private JLabel pathInfo = new JLabel("  ");
    private JLabel timeInfo = new JLabel("  ");

    private JMenuItem openFileItem, exitItem, findFileItem, viewLogItem, delLogItem, aboutItem;
    private JMenuItem switchSuanfa;


    ActionHandle handle = new ActionHandle();

    private JTextField proxyIP;
    private JTextField proxyPort;
    private JTextField urlLink;
    private JTextField threadNum;

    JButton trackButton, testingButton;
    JFileChooser fileChooser = new JFileChooser();

    JCheckBox checkBox = null;

    File src = null;
    File dest = null;
    File src2 = null;
    File dest2 = null;

    public UIFrame() {

        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);//可选择目录

        jf = new JFrame("pan-track");
        jf.setSize(750, 510);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        JPanel topJPanel = new JPanel();
        topJPanel.setLayout(new GridLayout(2, 1));

        JRootPane rootPane = new JRootPane(); // 此panel，添加菜单
        rootPane.setBackground(Color.gray);
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("文件(F)");
        JMenu commandMenu = new JMenu("命令(C)");
        JMenu toolMenu = new JMenu("工具(S)");
        JMenu optionMenu = new JMenu("选项(N)");
        JMenu helpMenu = new JMenu("帮助(H)");

        rootPane.setJMenuBar(menuBar);
        menuBar.add(fileMenu);
        menuBar.add(commandMenu);
        menuBar.add(toolMenu);
        menuBar.add(optionMenu);
        menuBar.add(helpMenu);

        findFileItem = new JMenuItem("查找文件");
        switchSuanfa = new JMenuItem("转换压缩格式");
        findFileItem.addActionListener(handle);
        switchSuanfa.addActionListener(handle);
        toolMenu.add(findFileItem);
        toolMenu.add(switchSuanfa);

        viewLogItem = new JMenuItem("查看日志");
        delLogItem = new JMenuItem("清除日志");
        viewLogItem.addActionListener(handle);
        delLogItem.addActionListener(handle);
        optionMenu.add(viewLogItem);
        optionMenu.add(delLogItem);

        openFileItem = new JMenuItem("打开压缩文件");
        exitItem = new JMenuItem("退出");
        openFileItem.addActionListener(handle);
        exitItem.addActionListener(handle);

        aboutItem = new JMenuItem("关于");
        helpMenu.add(aboutItem);
        aboutItem.addActionListener(handle);

        // 给菜单 添加菜单项
        fileMenu.add(openFileItem);
        fileMenu.add(exitItem);


        topJPanel.add(rootPane);// 工具panel :文件、编辑、查看
        jf.getContentPane().add(topJPanel, BorderLayout.NORTH);


        // center
        JPanel centerP = new JPanel();
        jf.getContentPane().add(centerP, BorderLayout.CENTER);

        JLabel label = new JLabel("测试代理");//待压缩的文件源

        proxyIP = new JTextField();
        proxyIP.setColumns(30);


        proxyPort = new JTextField();
        proxyPort.setColumns(10);

        //解压
        testingButton = new JButton("test");


        JPanel linePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        linePanel.add(label);
        linePanel.add(proxyIP);
        linePanel.add(proxyPort);
        linePanel.add(testingButton);
        centerP.add(linePanel);


        JLabel label2 = new JLabel("URL");

        urlLink = new JTextField();
        urlLink.setColumns(30);

        threadNum = new JTextField();
        threadNum.setColumns(10);

        trackButton = new JButton("track");

        centerP.add(label2);
        centerP.add(urlLink);
        centerP.add(threadNum);
        centerP.add(trackButton);

        trackButton.addActionListener(handle);
        testingButton.addActionListener(handle);

        jf.getContentPane().add(centerP, BorderLayout.CENTER);


        // south--状态栏
        JToolBar bottomToolBar = new JToolBar();
        bottomToolBar.setFloatable(false);// 设置JToolBar不可拖动

        bottomToolBar.setPreferredSize(new Dimension(jf.getWidth(), 20));
        bottomToolBar.add(leftInfo);

        // bottomToolBar.addSeparator(); //此方法添加分隔符 无效
        JSeparator jsSeparator = new JSeparator(SwingConstants.VERTICAL);
        bottomToolBar.add(jsSeparator);// 添加分隔符

        leftInfo.setPreferredSize(new Dimension(200, 20));
        leftInfo.setHorizontalTextPosition(SwingConstants.LEFT);

        bottomToolBar.add(pathInfo);
        pathInfo.setHorizontalTextPosition(SwingConstants.LEFT);
        bottomToolBar.add(new JSeparator(SwingConstants.VERTICAL));// 添加分隔符

        bottomToolBar.add(timeInfo);
        timeInfo.setPreferredSize(new Dimension(70, 20));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        timeInfo.setText(sdf.format(new Date()));

        jf.getContentPane().add(bottomToolBar, BorderLayout.SOUTH);// 下面--放“状态栏”

        jf.setVisible(true);
    }

    /**
     * 主界面菜单--事件监听
     *
     * @author ljheee
     */
    class ActionHandle implements ActionListener {

        File f = null;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == openFileItem) { //

            }

            if (e.getSource() == exitItem) {// exit application
                System.exit(0);
            }

            if (e.getSource() == aboutItem) {// show about
                JOptionPane.showMessageDialog(jf, "@Author:ljheee \n 2019");
            }

            if (e.getSource() == findFileItem) {// viewItem
                jf.repaint();
            }

            if (e.getSource() == switchSuanfa) {// 切换压缩算法
                //
            }

            if (e.getSource() == viewLogItem) {// 查看日志

            }

            if (e.getSource() == delLogItem) {// 删除日志

            }


            if (e.getSource() == trackButton) {//开始 暴力测试
                if(urlLink.getText() == null || "".equalsIgnoreCase(urlLink.getText())){
                    JOptionPane.showMessageDialog(jf, "url can't be empty.");
                }

                int threadNums = 1000;

                if(threadNum.getText() == null || "".equalsIgnoreCase(threadNum.getText())){
                }else {
                    threadNums = Integer.parseInt(threadNum.getText().trim());
                }

                //开始 破解
                new Execute().executeThread(urlLink.getText(), threadNums);
            }


            if (e.getSource() == testingButton) {//测试 代理
                NetUtil.checkProxy(proxyIP.getText().trim(), Integer.parseInt(proxyPort.getText().trim()));
            }

        }
    }


}