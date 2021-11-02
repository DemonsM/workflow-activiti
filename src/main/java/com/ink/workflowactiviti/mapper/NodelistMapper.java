package com.ink.workflowactiviti.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface NodelistMapper {
    int updateStatusById(@Param("updatedStatus") Integer updatedStatus, @Param("id") Integer id);
}
