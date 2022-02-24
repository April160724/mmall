package com.mmall.service;

import com.mmall.common.ServerResponse;

/**
 * @Author: July
 * @Description:
 * @Date Created in 2022-02-24 17:13
 * @Modified By:
 */
public interface IOrderService {
    ServerResponse pay(Long orderNo, Integer userId, String path);
}
