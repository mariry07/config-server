package com.optical.mapper;

import com.optical.bean.UserInfo;
import org.apache.ibatis.annotations.Param;

/**
 * Created by mary on 2021/4/14.
 */
public interface UserInfoMapper {

    Integer insert(UserInfo userInfo);

    UserInfo getById(@Param("id") Long id);

    Integer deleteById(@Param("id") Long id);



}
