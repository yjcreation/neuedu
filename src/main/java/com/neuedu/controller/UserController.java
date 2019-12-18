package com.neuedu.controller;

import com.neuedu.pojo.User;
import com.neuedu.service.UserService;
import com.neuedu.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

//@Controller//表示这是一个控制器的类
@RestController//下面所有的方法返回值都是json格式
@RequestMapping("/portal/")  //portal表示前端
public class UserController {

    /*//登录login.do?username=zhangsan&password=111
    @RequestMapping(value = {"/login.do","/login"})
    //@ResponseBody//单独表示一个方法表示前端返回一个json数据
    public User login(@RequestParam(value = "username", required = false, defaultValue = "")String usename,
                       @RequestParam("password") String password){
        User user = new User();
        user.setId(1);
        user.setUsername(usename);
        user.setPassword(password);

        return user;
    }

    @RequestMapping(value = {"/restful/login/{username}/{password}"})
    public User loginRestful(@PathVariable("username") String usename,
                             @PathVariable("password")String password){
        User user = new User();
        user.setId(1);
        user.setUsername(usename);
        user.setPassword(password);

        return user;
    }*/

    /*@Value("${jdbc.url}")
    private String jdbcUrl;

    @RequestMapping("/test")
    public String test(){
        return jdbcUrl;
    }*/
    @Autowired//表示注入
    UserService userService;//这里userService会报错，按住Alt+Enter，然后再按两次Enter，里面选择warning即可

    @RequestMapping("user/login.do")
    public ServerResponse login(String username, String password, HttpSession session){
        ServerResponse serverResponse = userService.loginLogic(username,password);
        if (serverResponse.isSucess()){
            session.setAttribute("CURRENT_USER",serverResponse.getData());
        }
        return serverResponse;
    }

    @RequestMapping("user/register.do")
    public ServerResponse register(User user){

        return userService.registerLogic(user);
    }
}
