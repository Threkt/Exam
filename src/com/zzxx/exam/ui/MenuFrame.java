package com.zzxx.exam.ui;

import com.zzxx.exam.controller.ClientContext;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 主菜单界面
 */
public class MenuFrame extends JFrame {
    private ClientContext controller;

    private JLabel info; // 记录用户的信息

    public MenuFrame() {
        init();
    }

    private void init() {
        setTitle("指针信息在线测评");
        setSize(600, 400);
        setContentPane(createContentPane());
        setLocationRelativeTo(null);

        //点X的时候...
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(-1);
            }
        });
    }

    //主界面
    private JPanel createContentPane() {
        JPanel pane = new JPanel(new BorderLayout());
        //加载标题图片
        ImageIcon icon = new ImageIcon(this.getClass().getResource("pic/title.png"));
        pane.add(BorderLayout.NORTH, new JLabel(icon));

        //中间的功能
        pane.add(BorderLayout.CENTER, createMenuPane());

        //右下角信息
        pane.add(BorderLayout.SOUTH, new JLabel("指针信息--版权所有 盗版必究", JLabel.RIGHT));

        return pane;
    }


    //中间的功能
    private JPanel createMenuPane() {
        JPanel pane = new JPanel(new BorderLayout());
        // 务必将 info 引用到界面控件对象
        info = new JLabel("XXX 同学您好!", JLabel.CENTER);//正中间的文字
        pane.add(BorderLayout.NORTH, info);

        //开始 分数 考试规则 离开 四个功能
        pane.add(BorderLayout.CENTER, createBtnPane());

        return pane;
    }

    //开始 分数 考试规则 离开 四个功能
    private JPanel createBtnPane() {
        JPanel pane = new JPanel(new FlowLayout());
        //调用了createImgBtn()方法
        JButton start = createImgBtn("pic/exam.png", "开始");
        JButton result = createImgBtn("pic/result.png", "分数");
        JButton msg = createImgBtn("pic/message.png", "考试规则");
        JButton exit = createImgBtn("pic/exit.png", "离开");

        pane.add(start);
        pane.add(result);
        pane.add(msg);
        pane.add(exit);

        //默认开始
        getRootPane().setDefaultButton(start);

        //点开始后..
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.kaishi();
            }
        });

        //点考试规则后..
        msg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                controller.msg();
            }
        });

        //点离开后..
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.exit();
            }
        });

        return pane;
    }

    // 创建图片按钮的方法
    private JButton createImgBtn(String img, String txt) {
        ImageIcon ico = new ImageIcon(this.getClass().getResource(img));

        JButton button = new JButton(txt, ico);
        // button.setIcon(ico);
        // 设置文本相对于图标的垂直位置
        button.setVerticalTextPosition(JButton.BOTTOM);
        // 设置文本相对于图标的水平位置
        button.setHorizontalTextPosition(JButton.CENTER);

        return button;
    }

    public void updateInfo(String info) {
        this.info.setText(info+" 同学您好!");
    }

    public void setInfo(JLabel info) {
        this.info = info;
    }

    public void setController(ClientContext controller) {
        this.controller = controller;
    }

    //测试
@Test
    public void test() {
        MenuFrame  m = new MenuFrame();
        m.setVisible(true);
    }

}