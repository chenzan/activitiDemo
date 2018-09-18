package com.act.demo.support.orm.version1;

/**
 * @author chenzan
 * @version V1.0
 * @description TODO
 * @create-date 2018/7/11
 * @modifier
 * @modifier-data
 */
public class DbContextHolder {
    public enum DbType {
        MASTER, SLAVE
    }

    private static final ThreadLocal<DbType> contextHolder = new ThreadLocal<>();

    public static void setDbType(DbType dbType) {
        if (dbType == null) throw new NullPointerException();
        contextHolder.set(dbType);
    }

    public static DbType getDbType() {
        return contextHolder.get() == null ? DbType.MASTER : contextHolder.get();
    }

    public static void clearDbType() {
        contextHolder.remove();
    }
}
