package com.act.demo.mapper;

import com.act.demo.domain.SysLeave;

public interface SysLeaveMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysLeave record);

    int insertSelective(SysLeave record);

    SysLeave selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysLeave record);

    int updateByPrimaryKey(SysLeave record);
}