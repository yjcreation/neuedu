package com.neuedu.service;

import com.neuedu.pojo.User;
import com.neuedu.utils.ServerResponse;

public interface UserService {
    //登录
    public ServerResponse loginLogic(String username, String password);

    //注册
    public ServerResponse registerLogic(User user);
}
