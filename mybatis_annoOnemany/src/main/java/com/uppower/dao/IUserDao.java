package com.uppower.dao;

import com.uppower.domain.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * @author 13536
 * @Company uppower
 * 在mybatis中针对，CRUD一共有4个注解
 * @Select @Insert @Update @Delete
 */
@CacheNamespace(blocking = true)
public interface IUserDao {

    /**
     * 查询所有用户
     * @return
     */
    @Select(value = "select * from user")
    @Results(id = "userMap",value = {
            @Result(id = true,column = "id",property = "userId"),
            @Result(column = "username",property = "userName"),
            @Result(column = "address",property = "userAddress"),
            @Result(column = "sex",property = "userSex"),
            @Result(column = "birthday",property = "userBirthday"),
            @Result(property = "accounts",column = "id",
                    many = @Many(select = "com.uppower.dao.IAccountDao.findAccountByUid",
                            fetchType = FetchType.LAZY))
    })
    List<User> findAll();

    /**
     * 根据id查找用户
     * @param userId
     */
    @Select("select * from user where id=#{id}")
    @ResultMap(value = "userMap")
    User findUserById(Integer userId);

    /**
     * 根据用户名模糊查找
     * @param username
     * @return
     */
    @Select("select * from user where username like #{username}")
    @ResultMap(value = "userMap")
    List<User> findUserByName(String username);
}
