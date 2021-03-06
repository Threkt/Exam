package com.zzxx.exam.entity;

import org.junit.Test;
import java.io.*;
import java.util.*;

/**
 * 数据库
 * 实体数据管理, 用来读取数据文件放到内存集合当中
 */
public class EntityContext {
    //key-id value - 用户对象
    private Map<String, User> users = new HashMap<>();
    //key - 难度 value - 难度级别对应的所有试题
    private Map<Integer, List<Question>> questions = new HashMap<>();
    private List<String> rules = new ArrayList<>();

    public EntityContext(){
        loadUsers("C:\\Users\\Administrator\\Desktop\\java\\ideaworks\\exam\\src\\com\\zzxx\\exam\\util\\user.txt");
        loadQuestions("C:\\Users\\Administrator\\Desktop\\java\\ideaworks\\exam\\src\\com\\zzxx\\exam\\util\\corejava.txt");
        loadRules("C:\\Users\\Administrator\\Desktop\\java\\ideaworks\\exam\\src\\com\\zzxx\\exam\\util\\rule.txt");
    }

    //读取user.txt，封装为user存到map里
    private void loadUsers(String filename) {
        BufferedReader br = null;
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
            while ((line = br.readLine()) != null) {
                if (!(line.startsWith("#") || line.equals(""))) {
                    String[] split = line.split("\\:");
                    User user = new User(split[1], Integer.valueOf(split[0]), split[2]);
                    user.setPhone(split[3]);
                    user.setEmail(split[4]);
                    users.put(split[0], user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    //读取corejava.txt（题目），存到List<Question>后根据难度存到map中
    private void loadQuestions(String filename) {
        BufferedReader br = null;
        String line;
        int count=0;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
            while ((line = br.readLine())!=null){
                List<Integer> answers = new ArrayList<>();
                List<String> option = new ArrayList<>();
                Question question = new Question();
                String[] split = line.split("\\,");
                String[] split1 = split[0].split("\\=");
                //answer
                if (split1[1].contains("/")){
                    String[] split1_1 = split1[1].split("\\/");
                    for (int i = 0; i < split1_1.length; i++) {
                        answers.add(Integer.valueOf(split1_1[i]));
                    }
                }else {
                    answers.add(Integer.valueOf(split1[1]));
                }
                question.setAnswers(answers);
                //score
                String[] split2 = split[1].split("\\=");
                question.setScore(Integer.valueOf(split2[1]));
                //level
                String[] split3 = split[2].split("\\=");
                question.setLevel(Integer.valueOf(split3[1]));
                //title
                line = br.readLine();
                question.setTitle(line);
                //ABCD
                line = br.readLine();
                option.add(line);
                line = br.readLine();
                option.add(line);
                line = br.readLine();
                option.add(line);
                line = br.readLine();
                option.add(line);
                question.setOptions(option);
                //id
                question.setId(count++);
                //type
                if (answers.size()==1){
                    question.setType(0);
                }
                if (answers.size()>1){
                    question.setType(1);
                }
                int level = question.getLevel();
                List<Question> list = questions.get(level);
                if (list==null){
                    list=new ArrayList<>();
                }
                list.add(question);
                questions.put(level,list);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //读取规则
    public void loadRules(String filename){
        BufferedReader br = null;
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
            while ((line=br.readLine())!=null){
                rules.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public void setUsers(Map<String, User> users) {
        this.users = users;
    }

    public Map<Integer, List<Question>> getQuestions() {
        return questions;
    }

    public void setQuestions(Map<Integer, List<Question>> questions) {
        this.questions = questions;
    }

    public List<String> getRules() {
        return rules;
    }

    public void setRules(List<String> rules) {
        this.rules = rules;
    }

    //根据用户ID，从数据库中查询用户对象
    public User findUserById(String id){
        return users.get(id);
    }

    //根据试题的难度级别，获得对应难度级别的试题列表
    public List<Question> findQuestionsByLevel(int level){
        List<Question> list = questions.get(level);
        return list;
    }









    @Test
    public void test() {
        EntityContext ec = new EntityContext();
        List<Question> questionsByLevel = ec.findQuestionsByLevel(1);
        for (Question question : questionsByLevel) {
            String s = question.toString();
            System.out.println(s);
        }


    }
}
