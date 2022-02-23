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

    @Override
    public ServerResponse del(Integer id, Integer shippingId) {
        int i = shippingMapper.deleteByShippingIdUseId(id, shippingId);
        if (i > 0) {
            return ServerResponse.createBySuccess("删除地址成功");
        }
        return ServerResponse.createByErrorMessage("删除地址失败");
    }

    @Override
    public ServerResponse update(Integer id, Shipping shipping) {
        shipping.setUserId(id);
        int insert = shippingMapper.updateByShipping(shipping);
        if (insert > 0) {

            return ServerResponse.createBySuccess("修改地址成功");
        }
        return ServerResponse.createByErrorMessage("修改地址失败");
    }
}
