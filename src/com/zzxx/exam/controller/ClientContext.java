package com.zzxx.exam.controller;

import com.zzxx.exam.Service.ExamService;
import com.zzxx.exam.Service.IdOrPwdException;
import com.zzxx.exam.entity.User;
import com.zzxx.exam.ui.*;

/**
 * 控制器:进行界面和业务模型之间的数据传递/交互
 * 调用数据和方法的地方
 */


public class ClientContext {
    private LoginFrame loginFrame;
    private MenuFrame menuFrame;
    private ExamFrame examFrame;
    private MsgFrame msgFrame;
    private WelcomeWindow welcomeWindow;
    private ExamService examService;

    public void start(){
        loginFrame.setVisible(true);
    }

    public void login(){
        String id = loginFrame.getIdField().getText();
        String psw = loginFrame.getPwdField().getText();
        try {
            User user = examService.login(id, psw);
            menuFrame.updateInfo(user.getName());
            loginFrame.setVisible(false);
            menuFrame.setVisible(true);
        } catch (IdOrPwdException e) {
            loginFrame.updateMessage(e.getMessage());
        }
    }

    public void setLoginFrame(LoginFrame loginFrame) {
        this.loginFrame = loginFrame;
    }

    public void setMenuFrame(MenuFrame menuFrame) {
        this.menuFrame = menuFrame;
    }

    public void setExamFrame(ExamFrame examFrame) {
        this.examFrame = examFrame;
    }

    public void setMsgFrame(MsgFrame msgFrame) {
        this.msgFrame = msgFrame;
    }

    public void setWelcomeWindow(WelcomeWindow welcomeWindow) {
        this.welcomeWindow = welcomeWindow;
    }

    public void setExamService(ExamService examService) {
        this.examService = examService;
    }
}
