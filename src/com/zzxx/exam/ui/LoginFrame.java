package com.zzxx.exam.ui;

import com.zzxx.exam.controller.ClientContext;
import org.junit.Test;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 登录界面 是一个具体窗口框
 */
public class LoginFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField idField;
    private JTextField pwdField;
    private JLabel message;
    private ClientContext controller;

    public void setController(ClientContext controller) {
        this.controller = controller;
    }

    public LoginFrame() {
        init();
    }

    /**
     * 初始化界面组件和布局的
     */
    private void init() {
        this.setTitle("登录系统");
        JPanel contentPane = createContentPane();
        this.setContentPane(contentPane);
        // 必须先设大小后居中
        setSize(300, 220);
        setLocationRelativeTo(null);

        //点X的时候...
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {

            }
        });
    }


    //主界面
    private JPanel createContentPane() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(new EmptyBorder(8, 8, 8, 8));
        p.add(BorderLayout.NORTH, new JLabel("登录考试系统", JLabel.CENTER));//上方文字
        p.add(BorderLayout.CENTER, createCenterPane());//编号 密码 输入框 和 下方信息
        p.add(BorderLayout.SOUTH, createBtnPane());//login center 按钮
        return p;
    }

    //login center 按钮
    private JPanel createBtnPane() {
        JPanel p = new JPanel(new FlowLayout());
        JButton login = new JButton("Login");
        JButton cancel = new JButton("Cancel");
        p.add(login);
        p.add(cancel);

        //默认选中login按钮
        getRootPane().setDefaultButton(login);

        //点login按钮...
        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //控制器开启登陆方法
                controller.login();
            }
        });

        //点cancel按钮...
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        return p;
    }

    //编号 密码 输入框 和 下方信息
    private JPanel createCenterPane() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(new EmptyBorder(8, 0, 0, 0));
        //login center 输入框
        p.add(BorderLayout.NORTH, createIdPwdPane());
        //下方信息
        message = new JLabel("", JLabel.CENTER);
        p.add(BorderLayout.SOUTH, message);
        return p;
    }

    //编号 密码 输入框
    private JPanel createIdPwdPane() {
        JPanel p = new JPanel(new GridLayout(2, 1, 0, 6));
        p.add(createIdPane());
        p.add(createPwdPane());
        return p;
    }

    //编号
    private JPanel createIdPane() {
        JPanel p = new JPanel(new BorderLayout(6, 0));
        p.add(BorderLayout.WEST, new JLabel("编号:"));
        JTextField idField = new JTextField();
        this.idField=idField;
        p.add(BorderLayout.CENTER, idField);

        return p;
    }

    /**
     * 简单工厂方法, 封装的复杂对象的创建过程, 返回一个对象实例
     */

    //密码
    private JPanel createPwdPane() {
        JPanel p = new JPanel(new BorderLayout(6, 0));
        p.add(BorderLayout.WEST, new JLabel("密码:"));
        JPasswordField pwdField = new JPasswordField();
        this.pwdField = pwdField;
        pwdField.enableInputMethods(true);
        p.add(BorderLayout.CENTER, pwdField);
        return p;
    }



    //获取编号
    public JTextField getIdField() {
        return idField;
    }

    //获取密码
    public JTextField getPwdField() {
        return pwdField;
    }

    //更新提示信息
    public void updateMessage(String message) {
        this.message.setText(message);
    }

    //测试
@Test
    public void test() {
        LoginFrame l = new LoginFrame();
        l.setVisible(true);
    }

}
