package com.mmall.service.impl;

import com.mmall.common.ServerResponse;
import com.mmall.dao.ShippingMapper;

import com.mmall.pojo.Shipping;
import com.mmall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: July
 * @Description:
 * @Date Created in 2022-02-23 16:51
 * @Modified By:
 */
@Service("iShipping")
public class ShippingImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;


    public ServerResponse add(Integer userId, Shipping shipping) {
        shipping.setUserId(userId);
        int insert = shippingMapper.insert(shipping);
        if (insert > 0) {
            Map map = new HashMap<>();
            map.put("shippingId", shipping.getId());
            return ServerResponse.createBySuccess("新建地址成功", map);
        }
        return ServerResponse.createByErrorMessage("新建地址失败");
    }
}
