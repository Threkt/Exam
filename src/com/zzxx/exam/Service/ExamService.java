package com.zzxx.exam.Service;

import com.zzxx.exam.entity.*;
import com.zzxx.exam.ui.ExamFrame;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 所有的业务模型: 登录, 开始考试, 查看规则, 交卷, 上一题, 下一题...
 * 将功能具体实现的方法
 */
public class ExamService {
    private EntityContext entityContext;
    private User user;//记录登陆的对象
    //login ：登陆功能
    public User login(String id ,String password) throws IdOrPwdException {
        Map<String, User> users = entityContext.getUsers();
        user = users.get(id);
        if (user!=null){
            if (password.equals(user.getPassword())){
                return user;
            }
        }
        throw new IdOrPwdException("编号/密码错误");
    }
    //msg:考试规则
    public String msg(){
        List<String> rules = entityContext.getRules();
        String s = "";
        for (String rule : rules) {
            s+=rule+"\n";
        }
        return s;
    }
    //kaishi : 开始
    public ExamInfo kaishi(User user){
        ExamInfo info = new ExamInfo();
        info.setTitle("通用");
        info.setTimeLimit(30);
        info.setQuestionCount(20);
        info.setUser(user);
        //生成题目
        createExamPaper();

        return info;
    }
    //定义一套试卷
    private List<QuestionInfo> paper = new ArrayList<>();

    //生成考试题目
    private void createExamPaper(){
        int index=0;
        for (int i = Question.LEVEL1; i <=Question.LEVEL10; i++) {
            List<Question> questions = entityContext.findQuestionsByLevel(i);
            //随机获得两个试题对象,并且加入到试卷中
            int a =(int)(Math.random()*questions.size());
            Question question1 = questions.get(a);
            int b=(int)(Math.random()*questions.size());
            while ( b==a){
                b=(int)(Math.random()*questions.size());
            }
            Question question2 = questions.get(b);
            QuestionInfo questionInfo = new QuestionInfo(index++,question1);
            paper.add(questionInfo);
            questionInfo = new QuestionInfo(index++,question2);
            paper.add(questionInfo);
        }
    }

    //通过序号寻找题目
    public Question getQuestionFormPaper(int i ){
        return paper.get(i).getQuestion();
    }

    public QuestionInfo getQuestionInfoFormPaper(int i ){
        return paper.get(i);
    }

    public List<Integer> getAnswersFromPaper(int i){
        return paper.get(i).getUserAnswers();
    }








    public void setEntityContext(EntityContext entityContext) {
        this.entityContext = entityContext;
    }

    public void getQuestionFormPaper() {
    }
}
