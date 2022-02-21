package com.mmall.controller.port;

import com.mmall.common.ServerResponse;
import com.mmall.service.IProductService;
import com.mmall.vo.ProductDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: July
 * @Description:port这个包下面的所有接口代码都是前台的代码
 * @Date Created in 2022-02-21 11:05
 * @Modified By:
 */
@RequestMapping("/product")
@Controller
public class ProductController {
    @Autowired
    private IProductService iProductService;

    //商品
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse<ProductDetailVo> detail(Integer productId) {
        return iProductService.getProductDetail(productId);
    }
}
