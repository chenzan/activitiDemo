package com.act.demo.support;

import com.act.demo._holder.SpringApplicationContextHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @param <B> 实体类
 * @param <M> dao
 */
public abstract class BaseService<B, M extends BaseMapper> {
    private Class<M> mClass;
    protected M mMapper;
    @Autowired
    SpringApplicationContextHelper springContextHolder;

    public BaseService() {
        Class<?> aClass = this.getClass();
        Type type = aClass.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
            mClass = (Class<M>) actualTypeArguments[1];
        } else {
            mClass = null;
        }
        mMapper = SpringApplicationContextHelper.getBean(mClass);
    }

    protected int insert(B b) {
        return mMapper.insert(b);
    }

    protected int insertSelective(B b) {
        return mMapper.insertSelective(b);
    }

    protected int deleteByPrimaryKey(Integer id) {
        return mMapper.deleteByPrimaryKey(id);
    }

    protected B selectByPrimaryKey(Integer id) {
        return (B) mMapper.selectByPrimaryKey(id);
    }

    protected int updateByPrimaryKey(B b) {
        return mMapper.updateByPrimaryKey(b);
    }

    protected int updateByPrimaryKeySelective(B b) {
        return mMapper.updateByPrimaryKeySelective(b);
    }

}
