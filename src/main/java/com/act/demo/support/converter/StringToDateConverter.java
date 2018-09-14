package com.act.demo.support.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 转化日期参数
 */
@Component
public class StringToDateConverter implements Converter<String, Date> {
    //spring内置的DateFormatter只能注释在实体类的实例变量
    @Override
    public Date convert(String s) {
        Date date = null;
        if (StringUtils.isNotEmpty(s)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                date = simpleDateFormat.parse(s);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }
}
