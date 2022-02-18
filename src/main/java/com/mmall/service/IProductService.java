package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.vo.ProductDetailVo;

/**
 * @Author: July
 * @Description:
 * @Date Created in 2022-02-18 15:31
 * @Modified By:
 */
public interface IProductService {

    ServerResponse addProduct(Product product);

    ServerResponse<String> setSaleStatus(Integer productId, Integer status);
    ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);
}
