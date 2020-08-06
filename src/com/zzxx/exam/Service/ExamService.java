package com.zzxx.exam.Service;

import com.zzxx.exam.entity.EntityContext;
import com.zzxx.exam.entity.User;

import java.util.Map;

/**
 * 所有的业务模型: 登录, 开始考试, 查看规则, 交卷, 上一题, 下一题...
 * 将功能具体实现的方法
 */
public class ExamService {
    private EntityContext entityContext;
    //login ：登陆功能
    public User login(String id ,String password) throws IdOrPwdException {
        Map<String, User> users = entityContext.getUsers();
        User user = users.get(id);
        if (user!=null){
            if (password.equals(user.getPassword())){
                return user;
            }
        }
        throw new IdOrPwdException("编号/密码错误");
    }










    public void setEntityContext(EntityContext entityContext) {
        this.entityContext = entityContext;
    }
}
