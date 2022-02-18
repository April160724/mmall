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
}
