package com.mmall.service.impl;

import com.mmall.common.ServerResponse;
import com.mmall.common.Const;
import com.mmall.common.TokenCash;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @Author: July
 * @Description:
 * @Date Created in 2022-01-29 16:05
 * @Modified By:
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        //查询我们输入的账户是否存在
        int resultCount = userMapper.checkUserName(username);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        //todo MD5密码登录
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        //加密一周传值
        User user = userMapper.selectLogin(username, md5Password);
        if (user == null) {
            return ServerResponse.createByErrorMessage("密码错误");
        }
        //数据匹配成功
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录ok", user);
    }

    //注册的数据操作
    public ServerResponse<String> register(User user) {
        //查询我们输入的账户是否存在,代码复用
       /* int resultCount = userMapper.checkUserName(user.getUsername());
        if (resultCount > 0) {
            return ServerResponse.createByErrorMessage("用户名已存在");
        }*/
        ServerResponse vaildResponse = this.checkValid(user.getUsername(), Const.USERNAME);
        if (!vaildResponse.isSuccess()) {
            return vaildResponse;
        }
        //检查Email是否存在
        int resultEmail = userMapper.checkEmail(user.getEmail());
        if (resultEmail > 0) {
            return ServerResponse.createByErrorMessage("邮箱已存在");
        }
        //0,普通用户,1 管理员
        user.setRole(Const.Role.ROLE_CUSTOMER);
        //密码需要加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int resultCount = userMapper.insert(user);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createBySuccess("注册成功");
    }

    //校验
    public ServerResponse<String> checkValid(String str, String type) {
        if (StringUtils.isNotBlank(type)) {
            //开始校验
            if (Const.USERNAME.equals(type)) {
                int resultCount = userMapper.checkUserName(str);
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("用户名已存在");
                }
            }
            if (Const.EMAIL.equals(type)) {
                int resultEmail = userMapper.checkEmail(str);
                if (resultEmail > 0) {
                    return ServerResponse.createByErrorMessage("邮箱已存在");
                }
            }
        } else {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        return ServerResponse.createBySuccess("校验成功");
    }

    //根据用户名去查找密保问题
    public ServerResponse selectQuestion(String username) {
        ServerResponse validResponse = this.checkValid(username, Const.USERNAME);
        if (validResponse.isSuccess()) {
            //用户不存在
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        String question = userMapper.selectQuestionByUserName(username);
        if (StringUtils.isNotBlank(question)) {
            return ServerResponse.createBySuccess(question);
        }
        return ServerResponse.createByErrorMessage("找回密码的问题是空的");
    }

    public ServerResponse<String> checkAnswer(String username, String question, String answer) {
        int resultCount = userMapper.checkAnswer(username, question, answer);
        if (resultCount > 0) {
            //说明回答的问题是对的额
            String forgetToken = UUID.randomUUID().toString();
            //本地的缓存就把token放进去了
            TokenCash.setKey(TokenCash.TOKEN_PREFIX + username, forgetToken);
            return ServerResponse.createBySuccess(forgetToken);
        }
        return ServerResponse.createByErrorMessage("问题的答案错误");
    }


    public ServerResponse<String> forgetRestPassword(
            String username, String passwordNew, String forgetToken) {
        //检验参数
        if (StringUtils.isBlank(forgetToken)) {
            return ServerResponse.createByErrorMessage("参数错误，需要传递正确的token");
        }
        //校验用户名
        ServerResponse validResponse = this.checkValid(username, Const.USERNAME);
        if (validResponse.isSuccess()) {
            //用户不存在
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        //获取token，改为调用常量
        /*String token=TokenCash.getkey("token_"+username);*/
        String token = TokenCash.getkey(TokenCash.TOKEN_PREFIX + username);
        //校验cash里面的token
        if (StringUtils.isBlank(token)) {
            return ServerResponse.createByErrorMessage("token过期啦");
        }
        //做一个判断
        if (StringUtils.equals(forgetToken, token)) {
            String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
            int rowCount = userMapper.updatePasswordByUsername(username, md5Password);

            if (rowCount > 0) {
                return ServerResponse.createBySuccessMessage("修改密码成功");
            }
        } else {
            return ServerResponse.createByErrorMessage("请重新获取重置密码的token");
        }
        return ServerResponse.createByErrorMessage("修改密码失败");
    }


    public ServerResponse<String> restPaasowrd(String passwordOld, String passwordNew,User user) {
        //防止横向越权，校验所属密码的所属用户是否对应
        int resultCount=userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld),user.getId());
        if (resultCount==0){
            return ServerResponse.createByErrorMessage("旧密码错误");
        }
        String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
        user.setPassword(md5Password);
        int rowCount = userMapper.updateByPrimaryKeySelective(user);
        if (rowCount > 0) {
            return ServerResponse.createBySuccessMessage("修改密码成功");
        }
        return ServerResponse.createByErrorMessage("修改密码失败");
    }

}
