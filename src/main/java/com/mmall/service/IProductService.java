package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;

/**
 * @Author: July
 * @Description:
 * @Date Created in 2022-02-18 15:31
 * @Modified By:
 */
public interface IProductService {

    ServerResponse addProduct(Product product);
}
