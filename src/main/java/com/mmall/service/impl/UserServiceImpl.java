package com.mmall.service.impl;

import com.mmall.common.ServerResponse;
import com.mmall.common.Const;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        ServerResponse vaildResponse = this.checkValid(user.getUsername(),Const.USERNAME);
        if (!vaildResponse.isSuccess()){
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
}
