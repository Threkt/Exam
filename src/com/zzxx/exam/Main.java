package com.zzxx.exam;

import com.zzxx.exam.Service.ExamService;
import com.zzxx.exam.controller.ClientContext;
import com.zzxx.exam.entity.EntityContext;
import com.zzxx.exam.ui.*;

public class Main {
    public static void main(String[] args) {
        ClientContext context = new ClientContext();
        LoginFrame loginFrame = new LoginFrame();
        ExamService examService = new ExamService();
        MenuFrame menuFrame = new MenuFrame();
        EntityContext entityContext = new EntityContext();
        MsgFrame msgFrame = new MsgFrame();
        ExamFrame examFrame = new ExamFrame();
        WelcomeWindow welcomeWindow = new WelcomeWindow();

        //互相套娃
        context.setWelcomeWindow(welcomeWindow);
        examFrame.setClientContext(context);
        context.setExamFrame(examFrame);
        context.setMsgFrame(msgFrame);
        menuFrame.setController(context);
        examService.setEntityContext(entityContext);
        loginFrame.setController(context);
        context.setLoginFrame(loginFrame);
        context.setExamService(examService);
        context.setMenuFrame(menuFrame);


        context.start();


    }
}
