package com.zzxx.exam.controller;

import com.zzxx.exam.Service.ExamService;
import com.zzxx.exam.Service.IdOrPwdException;
import com.zzxx.exam.entity.ExamInfo;
import com.zzxx.exam.entity.Question;
import com.zzxx.exam.entity.QuestionInfo;
import com.zzxx.exam.entity.User;
import com.zzxx.exam.ui.*;
import org.w3c.dom.ls.LSOutput;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.*;

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
    private User user;
    private QuestionInfo questionInfo;

    //开始
    public void start() {
        welcomeWindow.setVisible(true);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                welcomeWindow.setVisible(false);
                loginFrame.setVisible(true);
            }
        }, 3000L);
    }

    //点击login
    public void login() {
        String id = loginFrame.getIdField().getText();
        String psw = loginFrame.getPwdField().getText();
        try {
            user = examService.login(id, psw);
            menuFrame.updateInfo(user.getName());
            loginFrame.setVisible(false);
            menuFrame.setVisible(true);
        } catch (IdOrPwdException e) {
            loginFrame.updateMessage(e.getMessage());
        }
    }

    //点msg(考试规则)
    public void msg() {
        String msg = examService.msg();
        msgFrame.showMsg(msg);
        msgFrame.setVisible(true);
    }

    private long m = 30L;
    private long s = 0L;

    //点start
    public void kaishi() {
        //获得用户信息后更新 用户 考试 考试时间
        ExamInfo info = examService.kaishi(user);
        examFrame.updateInfo(info.getUser().getName(), info.getTitle(), info.getTimeLimit());

        //获取考试题目（第一个默认为0）并更新
        Question questionFormPaper = examService.getQuestionFormPaper(questionIndex);
        examFrame.updateQuestionArea(questionFormPaper.toString());

        //剩余时间
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                if (m==0&&s==0){
                    jiaojuan();
                }
                if (s == 0) {
                    examFrame.updateTime(0l, m--, s);
                    s = 59L;
                }
                examFrame.updateTime(0l, m, s--);
            }
        }, 0, 1000L);


        //更新界面
        menuFrame.setVisible(false);
        examFrame.setVisible(true);
    }

    //点离开
    public void exit() {
        menuFrame.setVisible(false);
        loginFrame.setVisible(true);
    }

    //题目下标
    private int questionIndex = 0;

    //下一题
    public void next() {
        if (questionIndex < 19) {
            List<Integer> list = new ArrayList<>();
            ExamFrame.Option[] options = examFrame.getOptions();
            for (int i = 0; i < 4; i++) {
                if (options[i].isSelected()) {
                    list.add(i);
                }
            }
            questionInfo = examService.getQuestionInfoFormPaper(questionIndex);
            questionInfo.setUserAnswers(list);


            questionIndex++;
            Question questionFormPaper = examService.getQuestionFormPaper(questionIndex);
            examFrame.updateQuestionArea(questionFormPaper.toString());
            examFrame.updateQuestionCount(questionIndex + 1);


            for (int i = 0; i < 4; i++) {
                options[i].setSelected(false);
            }
            questionInfo = examService.getQuestionInfoFormPaper(questionIndex);
            List<Integer> userAnswers = questionInfo.getUserAnswers();
            for (int i = 0; i < userAnswers.size(); i++) {
                for (int i1 = 0; i1 < 4; i1++) {
                    if (userAnswers.get(i) == i1) {
                        options[i1].setSelected(true);
                    }
                }
            }
        }
    }

    //上一题
    public void last() {
        if (questionIndex > 0) {
            List<Integer> list = new ArrayList<>();
            ExamFrame.Option[] options = examFrame.getOptions();
            for (int i = 0; i < 4; i++) {
                if (options[i].isSelected()) {
                    list.add(i);
                }
            }
            questionInfo = examService.getQuestionInfoFormPaper(questionIndex);
            questionInfo.setUserAnswers(list);

            questionIndex--;
            Question questionFormPaper = examService.getQuestionFormPaper(questionIndex);
            examFrame.updateQuestionArea(questionFormPaper.toString());
            examFrame.updateQuestionCount(questionIndex + 1);

            for (int i = 0; i < 4; i++) {
                options[i].setSelected(false);
            }
            questionInfo = examService.getQuestionInfoFormPaper(questionIndex);
            List<Integer> userAnswers = questionInfo.getUserAnswers();
            for (int i = 0; i < userAnswers.size(); i++) {
                for (int i1 = 0; i1 < 4; i1++) {
                    if (userAnswers.get(i) == i1) {
                        options[i1].setSelected(true);
                    }
                }
            }

        }
    }

    private int zongfeng = 0;

    //交卷
    public void jiaojuan() {
        List<Integer> list = new ArrayList<>();
        ExamFrame.Option[] options = examFrame.getOptions();
        for (int i = 0; i < 4; i++) {
            if (options[i].isSelected()) {
                list.add(i);
            }
        }
        questionInfo = examService.getQuestionInfoFormPaper(questionIndex);
        questionInfo.setUserAnswers(list);

        //计算总分
        for (int i = 0; i < questionIndex; i++) {
            List<Integer> answersFromPaper = examService.getAnswersFromPaper(i);
            Question questionFormPaper = examService.getQuestionFormPaper(i);
            List<Integer> answers = questionFormPaper.getAnswers();
            if (answers.size() == answersFromPaper.size() && answers.containsAll(answersFromPaper)) {
                zongfeng += questionFormPaper.getScore();
            }
        }

        //保存成绩到txt中
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("成绩.txt")), true);
            pw.println(user.getName() + "的成绩为：" + zongfeng);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }

        examFrame.setVisible(false);
        menuFrame.setVisible(true);
    }

    //点成绩
    public void result(){
        msgFrame.showMsg(user.getName() + "的成绩为：" + zongfeng);
        msgFrame.setVisible(true);
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
