package com.optical.mapper;

import com.optical.bean.ResidentLog;
import org.apache.ibatis.annotations.Param;


/**
 * Created by mary on 2021/4/14.
 */
public interface ResidentLogMapper {

    void insert(ResidentLog residentInfo);

    ResidentLog getByResidentCode(@Param("code") String code);

    ResidentLog getById( @Param("id") Long id);

    ResidentLog getByResidentId( @Param("residentId") Long residentId);

    Integer deleteById(@Param("id") Long id);


}
