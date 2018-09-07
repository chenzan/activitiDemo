package com.act.demo.support;

public interface BaseMapper<T> {
    int deleteByPrimaryKey(Integer id);

    int insert(T t);

    int insertSelective(T t);

    T selectByPrimaryKey(T id);

    int updateByPrimaryKeySelective(T t);

    int updateByPrimaryKey(T t);
}
