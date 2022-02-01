package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

/**
 * @Author: July
 * @Description:
 * @Date Created in 2022-01-29 16:04
 * @Modified By:
 */
public interface IUserService {

    ServerResponse<User> login(String username, String password);
}
