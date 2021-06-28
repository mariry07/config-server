package com.optical.mapper;

import com.optical.bean.UserInfo;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Param;

/**
 * Created by mary on 2021/4/14.
 */
public interface UserInfoMapper {

    Integer insert(UserInfo userInfo);

    UserInfo getById(@Param("id") Long id);

    UserInfo getByUserCode(@Param("userCode") String userCode);

    UserInfo getByVendorId(@Param("vendorId") Long vendorId);

    Integer deleteById(@Param("id") Long id);

    UserInfo getTags(@Param("vendorId") Long vendorId);

}
