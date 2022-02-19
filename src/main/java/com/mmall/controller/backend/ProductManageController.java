package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @Author: July
 * @Description:
 * @Date Created in 2022-02-18 15:30
 * @Modified By:
 */
@Controller
@RequestMapping("/manage/product")
public class ProductManageController {

    @Autowired
    private IProductService iProductService;
    @Autowired
    private IUserService iUserService;

    @RequestMapping("save.do")
    @ResponseBody
    public ServerResponse productSave(HttpSession session, Product product) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "用户未登录，请登录管理员");
        }
        //校验一下是否是管理员
        if (iUserService.checkAdmin(user).isSuccess()) {
            //yes
            //增加产品的业务逻辑
            return iProductService.addProduct(product);


        } else {
            return ServerResponse.createByErrorMessage("无管理员权限操作，需要管理员权限");
        }
    }


    @RequestMapping("set_sale_status.do")
    @ResponseBody
    public ServerResponse upAndDown(HttpSession session, Integer productId, Integer status) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "用户未登录，请登录管理员");
        }
        //校验一下是否是管理员
        if (iUserService.checkAdmin(user).isSuccess()) {
            //yes
            //增加产品的业务逻辑
            return iProductService.setSaleStatus(productId, status);


        } else {
            return ServerResponse.createByErrorMessage("无管理员权限操作，需要管理员权限");
        }
    }


    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse getDetailwn(HttpSession session, Integer productId, Integer status) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "用户未登录，请登录管理员");
        }
        //校验一下是否是管理员
        if (iUserService.checkAdmin(user).isSuccess()) {
            //yes
            //获取产品详情
            return iProductService.manageProductDetail(productId);


        } else {
            return ServerResponse.createByErrorMessage("无管理员权限操作，需要管理员权限");
        }
    }

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse getList(HttpSession session,
                                  @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "用户未登录，请登录管理员");
        }
        //校验一下是否是管理员
        if (iUserService.checkAdmin(user).isSuccess()) {
            //yes
            //获取动态分页，用哪个pagehelper
            return iProductService.getProductList(pageNum, pageSize);

        } else {
            return ServerResponse.createByErrorMessage("无管理员权限操作，需要管理员权限");
        }
    }
}
