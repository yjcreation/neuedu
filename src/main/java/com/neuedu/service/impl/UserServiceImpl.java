package com.neuedu.service.impl;

import com.neuedu.common.Const;
import com.neuedu.common.ResponseCode;
import com.neuedu.dao.UserMapper;
import com.neuedu.pojo.User;
import com.neuedu.service.UserService;
import com.neuedu.utils.DateUtil;
import com.neuedu.utils.MD5Utils;
import com.neuedu.utils.ServerResponse;
import com.neuedu.vo.UserVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service//bean得交给IoC容器管理，得加上service注解，这样UserService才会实例化
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Override
    public ServerResponse loginLogic(String username, String password) {
        //1.用户名密码非空判断 StringUtils.isEmpty()
        //StringUtils.isBlank()在isEmpty()的基础上再判断是否有制表符、空格等
        if (StringUtils.isBlank(username)){//null,length=0,含有空格  tab制表符
            return  ServerResponse.createServerResponseByFail(ResponseCode.USERNAME_NOT_EMPTY.getCode(),ResponseCode.USERNAME_NOT_EMPTY.getMsg());
        }
        if (StringUtils.isBlank(password)){
            return  ServerResponse.createServerResponseByFail(ResponseCode.PASSWORD_NOT_EMPTY.getCode(),ResponseCode.USERNAME_NOT_EMPTY.getMsg());
        }
        //2.查看用户名是否存在
        if (userMapper.findByUsername(username)==0){
            return ServerResponse.createServerResponseByFail(ResponseCode.USERNAME_NOT_EXIST.getCode(),ResponseCode.USERNAME_NOT_EXIST.getMsg());
        }
        //3.根据用户名密码查询
        User user = userMapper.findByUsernameAndPassword(username,MD5Utils.getMD5Code(password));
        if (user==null){
            return ServerResponse.createServerResponseByFail(ResponseCode.PASSWORD_ERROR.getCode(),ResponseCode.PASSWORD_ERROR.getMsg());
        }
        //4.返回结果
        return ServerResponse.createServerResponseBySucess(convert(user));
    }

    public UserVO convert(User user){
        UserVO userVO = new UserVO();
        user.setId(user.getId());
        userVO.setUsername(user.getUsername());
        userVO.setEmail(user.getEmail());
        userVO.setPhone(user.getPhone());
        userVO.setCreateTime(DateUtil.date2String(user.getCreateTime()));
        userVO.setUpdateTime(DateUtil.date2String(user.getUpdateTime()));
        return userVO;
    }

    @Override
    public ServerResponse registerLogic(User user) {
        if (user==null){
            return ServerResponse.createServerResponseByFail(ResponseCode.PARAMETER_NOT_NULL.getCode(),ResponseCode.PARAMETER_NOT_NULL.getMsg());
        }
        String username = user.getUsername();
        String password = user.getPassword();
        String email = user.getEmail();
        String phone = user.getPhone();
        String question = user.getQuestion();
        String answer = user.getAnswer();
        //1.用户名密码非空判断
        if (username==null||username.equals("")){
            return  ServerResponse.createServerResponseByFail(ResponseCode.USERNAME_NOT_EMPTY.getCode(),ResponseCode.USERNAME_NOT_EMPTY.getMsg());
        }
        if (password==null||password.equals("")){
            return  ServerResponse.createServerResponseByFail(ResponseCode.PASSWORD_NOT_EMPTY.getCode(),ResponseCode.USERNAME_NOT_EMPTY.getMsg());
        }
        if (email==null||email.equals("")){
            return  ServerResponse.createServerResponseByFail(ResponseCode.EMAIL_NOT_NULL.getCode(),ResponseCode.EMAIL_NOT_NULL.getMsg());
        }
        if (phone==null||phone.equals("")){
            return  ServerResponse.createServerResponseByFail(ResponseCode.PHONE_NOT_NULL.getCode(),ResponseCode.PHONE_NOT_NULL.getMsg());
        }
        if (question==null||question.equals("")){
            return  ServerResponse.createServerResponseByFail(ResponseCode.QUESTION_NOT_NULL.getCode(),ResponseCode.QUESTION_NOT_NULL.getMsg());
        }
        if (answer==null||answer.equals("")){
            return  ServerResponse.createServerResponseByFail(ResponseCode.ANSWER_NOT_NULL.getCode(),ResponseCode.ANSWER_NOT_NULL.getMsg());
        }
        //2.查看用户名是否存在
        if (userMapper.findByUsername(username)>0){
            return ServerResponse.createServerResponseByFail(ResponseCode.USERNAME_EXIST.getCode(),ResponseCode.USERNAME_EXIST.getMsg());
        }
        //3.判断邮箱是否存在
        if (userMapper.findByEmail(email)>0){
            return ServerResponse.createServerResponseByFail(ResponseCode.EMAIL_EXIST.getCode(),ResponseCode.EMAIL_EXIST.getMsg());
        }
        //4.注册
        user.setPassword(MD5Utils.getMD5Code(user.getPassword()));
        user.setRole(Const.NORMAL_USER);//设置用户的角色
        if (userMapper.insert(user)==0){
            return ServerResponse.createServerResponseByFail(ResponseCode.REGISTER_ERROR.getCode(),ResponseCode.REGISTER_ERROR.getMsg());
        }

        return ServerResponse.createServerResponseBySucess();
    }
}
