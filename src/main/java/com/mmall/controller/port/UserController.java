package com.mmall.controller.port;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

/**
 * @Author: July
 * @Description:
 * @Date Created in 2022-01-29 16:00
 * @Modified By:
 */
@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService iUserService;

    /*
     * @Des 登录接口
     * @Date 2022/2/1 11:21
     * @Param username，password
     * @Return
     */
    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session) {
        ServerResponse<User> login = iUserService.login(username, password);
        if (login.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, login.getData());
        }
        return login;
    }
    /*
     * @Des 登出接口，就是直接把session 里面的用户数据清除就好了
     * @Date 2022/2/1 11:24
     * @Param
     * @Return
     */

    @RequestMapping(value = "logout.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    //注册
    @RequestMapping(value = "register.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> register(User user){
        return iUserService.register(user);
    }
    //实时校验功能的开发
    @RequestMapping(value = "check_valid.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> checkValid(String str,String type){
        return iUserService.checkValid(str,type);
    }


    //获取当前用户登录的信息
    @RequestMapping(value = "get_user_info.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session){
        //根据当前的
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user!=null){
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessage("当前用户未登录,无法获取当前信息");
    }

    //忘记密码
    @RequestMapping(value = "forget_get_question.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String > forgetGetQuestion(String username){
        return iUserService.selectQuestion(username);
    }
}
