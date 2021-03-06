package com.mmall.dao;

import com.mmall.pojo.Cart;
import com.mmall.pojo.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    Cart selectCartByUserIdProductId(@Param("userId") Integer userId, @Param("productId") Integer productId);

    List<Cart> selectCartByUserId(Integer userId);

    int selectCartProductCheckedStatusByUserId(Integer userId);

    int deleteByProductList(@Param("userId") Integer userId,
                            @Param("productId") List<String> productList);

    void checkedOrUncheckedProduct(@Param("userId") Integer userI,
                                   @Param("productId") Integer productId,
                                   @Param("checked") Integer checked);

    int selectCartProductCount(Integer userId);
}