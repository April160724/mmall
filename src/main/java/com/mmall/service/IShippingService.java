package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Shipping;

/**
 * @Author: July
 * @Description:
 * @Date Created in 2022-02-23 16:50
 * @Modified By:
 */
public interface IShippingService {

    ServerResponse add(Integer userId, Shipping shipping);
}
