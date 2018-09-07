package com.act.demo.mapper;

import com.act.demo.domain.SysLeave;
import com.act.demo.support.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysLeaveMapper extends BaseMapper<SysLeave> {

    List<SysLeave> selectAll(Integer userId);

    List<SysLeave> selectByUserId(int userId);
}