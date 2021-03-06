package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
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
 * @Date Created in 2022-02-18 10:13
 * @Modified By:
 */
@Controller
@RequestMapping("/manage/category")
public class CategoryManageController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private ICategoryService iCategoryService;

    @RequestMapping("add_category.do")
    @ResponseBody//自动使用jsckson序列化
    public ServerResponse addCategory(HttpSession session,
                                      String categoryName,
                                      @RequestParam(value = "parentId", defaultValue = "0") int parentId) {
        //判断是否登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "用户未登录");
        }
        //校验一下是否是管理员
        if (iUserService.checkAdmin(user).isSuccess()) {
            //yes
            //增加我们处理分类的逻辑
            return iCategoryService.addCategory(categoryName, parentId);
        } else {
            return ServerResponse.createByErrorMessage("无管理员权限操作，需要管理员权限");
        }
    }

    @RequestMapping("set_category_name.do")
    @ResponseBody//自动使用jsckson序列化
    public ServerResponse setCategoryName(HttpSession session,
                                          String categoryName, Integer categoryId) {
        //判断是否登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "用户未登录");
        }
        //校验一下是否是管理员
        if (iUserService.checkAdmin(user).isSuccess()) {
            //yes
            //更新我们的品类名
            return iCategoryService.updateCategoryName(categoryName, categoryId);
        } else {
            return ServerResponse.createByErrorMessage("无管理员权限操作，需要管理员权限");
        }
    }

    @RequestMapping("get_category.do")
    @ResponseBody//自动使用jsckson序列化
    public ServerResponse getChildParallelCategory(HttpSession session,
                                                   @RequestParam(value = "categoryId", defaultValue = "0") Integer categotyId) {
        //判断是否登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "用户未登录");
        }
        //校验一下是否是管理员
        if (iUserService.checkAdmin(user).isSuccess()) {
            //yes
            //查询子节点的信息,不递归
            return iCategoryService.getchildrenCategory(categotyId);

        } else {
            return ServerResponse.createByErrorMessage("无管理员权限操作，需要管理员权限");
        }
    }

    /**
     * @Des 递归查询
     * @Date 2022/2/18 11:43
     * @Param
     * @Return
     */
    @RequestMapping("get_deep_category.do")
    @ResponseBody//自动使用jsckson序列化
    public ServerResponse getChildrenDeepCategory(HttpSession session,
                                                  @RequestParam(value = "categoryId", defaultValue = "0") Integer categotyId) {
        //判断是否登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "用户未登录");
        }
        //校验一下是否是管理员
        if (iUserService.checkAdmin(user).isSuccess()) {
            //yes
            //查询子节点的信息,递归查询
            return iCategoryService.selectCategoryAndChildreById(categotyId);

        } else {
            return ServerResponse.createByErrorMessage("无管理员权限操作，需要管理员权限");
        }
    }
}
