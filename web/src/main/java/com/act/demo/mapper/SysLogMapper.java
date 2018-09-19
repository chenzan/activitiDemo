package com.act.demo.mapper;

import com.act.demo.domain.SysLog;
import com.act.demo.support.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysLogMapper extends BaseMapper<SysLog> {

    int updateByPrimaryKeyWithBLOBs(SysLog record);
}