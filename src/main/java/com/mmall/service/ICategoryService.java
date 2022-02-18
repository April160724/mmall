package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;

import java.util.List;

/**
 * @Author: July
 * @Description:
 * @Date Created in 2022-02-18 10:28
 * @Modified By:
 */
public interface ICategoryService {
    public ServerResponse addCategory(String categoryName, Integer parentId);

    public ServerResponse updateCategoryName(
            String categoryName, Integer categoryId);

    public ServerResponse<List<Category>> getchildrenCategory(Integer categoryId);
    public ServerResponse selectCategoryAndChildreById(Integer categoryId);
}
