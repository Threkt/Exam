package com.zzxx.exam.ui;

import com.zzxx.exam.controller.ClientContext;
import org.junit.Test;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class ExamFrame extends JFrame {
    private JLabel  examInfo;
    private ClientContext clientContext;
    // 选项集合, 方便答案读取的处理
    private Option[] options = new Option[4];

    public ExamFrame() {
        init();
    }

    private void init() {
        setTitle("指针信息在线测评");
        setSize(600, 380);
        setContentPane(createContentPane());
        setLocationRelativeTo(null);

        //点x的时候..
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {// 正在关闭的时候
                System.exit(-1);
            }
        });
    }

    //主界面
    private JPanel createContentPane() {
        JPanel pane = new JPanel(new BorderLayout());
        pane.setBorder(new EmptyBorder(6, 6, 6, 6));
        //标题图片
        ImageIcon icon = new ImageIcon(getClass().getResource("pic/exam_title.png"));
        pane.add(BorderLayout.NORTH, new JLabel(icon));
        //中间
        pane.add(BorderLayout.CENTER, createCenterPane());
        //题目下标 按钮 剩余时间
        pane.add(BorderLayout.SOUTH, createToolsPane());

        return pane;
    }




    private JPanel createCenterPane() {
        JPanel pane = new JPanel(new BorderLayout());
        // 注意!
        examInfo = new JLabel("姓名:XXX 考试:XXX 考试时间:XXX", JLabel.CENTER);
        pane.add(BorderLayout.NORTH, examInfo);

        //题目内容
        pane.add(BorderLayout.CENTER, createQuestionPane());
        //选项
        pane.add(BorderLayout.SOUTH, createOptionsPane());
        return pane;
    }

    //选项
    private JPanel createOptionsPane() {
        JPanel pane = new JPanel();
        Option a = new Option(0, "A");
        Option b = new Option(1, "B");
        Option c = new Option(2, "C");
        Option d = new Option(3, "D");
        options[0] = a;
        options[1] = b;
        options[2] = c;
        options[3] = d;
        pane.add(a);
        pane.add(b);
        pane.add(c);
        pane.add(d);
        return pane;
    }

    //题目内容
    private JScrollPane createQuestionPane() {
        JScrollPane pane = new JScrollPane();
        pane.setBorder(new TitledBorder("题目"));// 标题框

        // 注意!
        questionArea = new JTextArea();
        questionArea.setText("问题\nA.\nB.");
        questionArea.setLineWrap(true);// 允许折行显示
        questionArea.setEditable(false);// 不能够编辑内容
        // JTextArea 必须放到 JScrollPane 的视图区域中(Viewport)
        pane.getViewport().add(questionArea);
        return pane;
    }

    //题目下标 按钮 剩余时间
    private JPanel createToolsPane() {
        JPanel pane = new JPanel(new BorderLayout());
        pane.setBorder(new EmptyBorder(0, 10, 0, 10));
        // 注意!   这里目前都是不变的固定值
        questionCount = new JLabel("题目:20 的 1题");

        timer = new JLabel("剩余时间:222秒");

        pane.add(BorderLayout.WEST, questionCount);//题目
        pane.add(BorderLayout.EAST, timer);//时间
        //上一题 下一题 交卷
        pane.add(BorderLayout.CENTER, createBtnPane());
        return pane;
    }

    //上一题 下一题 交卷
    private JPanel createBtnPane() {
        JPanel pane = new JPanel(new FlowLayout());
        prev = new JButton("上一题");
        next = new JButton("下一题");
        JButton send = new JButton("交卷");

        pane.add(prev);
        pane.add(next);
        pane.add(send);

        //点上一题..
        prev.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clientContext.last();
            }
        });
        //点下一题..
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clientContext.next();
            }
        });
        //点交卷..
        send.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clientContext.jiaojuan();
            }
        });
        return pane;
    }


    /**
     * 使用内部类扩展了 JCheckBox 增加了val 属性, 代表答案值
     */
    public class Option extends JCheckBox {
        int value;

        public Option(int val, String txt) {
            super(txt);
            this.value = val;
        }
    }

    private JTextArea questionArea;

    private JButton next;

    private JButton prev;

    private JLabel questionCount;

    private JLabel timer;

    //更新剩余时间
    public void updateTime(long h, long m, long s) {
        String time ="剩余时间："+ h + ":" + m + ":" + s;
        if (m < 5) {
            timer.setForeground(new Color(0xC85848));
        } else {
            timer.setForeground(Color.blue);
        }
        timer.setText(time);
    }
    //更新 题目
    public void updateQuestionArea(String questionArea){
        this.questionArea.setText(questionArea);
    }

    //更新姓名 考试 考试时间
    public void updateInfo(String name,String title,int time) {
        examInfo.setText("姓名:"+name+" 考试: "+title+" 考试时间:"+time+"分钟");
    }

    //更新姓名 考试 考试时间
    public void updateQuestionCount(int i) {
        questionCount.setText("题目:20 的第 "+i+"题");
    }


    public void setClientContext(ClientContext clientContext) {
        this.clientContext = clientContext;
    }

    public Option[] getOptions() {
        return options;
    }




    //测试
    @Test
    public void test() {
        ExamFrame e = new ExamFrame();
        e.setVisible(true);
    }
}