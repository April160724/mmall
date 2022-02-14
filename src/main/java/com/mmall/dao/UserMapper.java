package com.mmall.dao;

import com.mmall.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    //check the user
    int checkUserName(String username);

    //login method
    User selectLogin(@Param("username") String username, @Param("password") String password);

    int checkEmail(String email);

    String selectQuestionByUserName(String username);

    int checkAnswer(@Param("username") String username,
                    @Param("question") String question,
                    @Param("answer") String answer);

    int updatePasswordByUsername(@Param("username") String username, @Param("passwordNew") String passwordNew);
}