package com.zzxx.exam.util;

import com.zzxx.exam.ui.ExamFrame;

import java.io.IOException;
import java.util.Properties;

/**
 * Config 读取系统的配置文件
 */
public class Config {
    private Properties pro = new Properties();

    public Config(String file) {
        try {
            pro.load(Config.class.getResourceAsStream(file));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public int getInt(String key) {
        return Integer.parseInt(pro.getProperty(key));
    }

    public String getString(String key) {
        return pro.getProperty(key);
    }

    //测试
/*
    public static void main(String[] args) {
        Config config =new Config("config.properties");
        int questionNumber = config.getInt("QuestionNumber");
        System.out.println(questionNumber);//20
    }
*/
}