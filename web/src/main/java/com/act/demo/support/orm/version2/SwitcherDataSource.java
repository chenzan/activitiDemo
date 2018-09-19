package com.act.demo.support.orm.version2;

import com.act.demo._holder.SpringApplicationContextHelper;
import com.act.demo.config.DataSourceConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * 数据源切换2 自定义切换的数据源类
 */
@Slf4j
@Primary
@Component("dataSource")
public class SwitcherDataSource implements DataSource {
    //当前数据源
    private ThreadLocal<DataSource> dataSource = new ThreadLocal<>();
    //默认的主数据源
    @Resource(name = "masterDataSource")
    DataSource defaultDataSource;

    /**
     * 从线程局部变量中获取当前数据源
     *
     * @return
     */
    private DataSource getDataSource() {
        DataSource currDataSource = this.dataSource.get();
        if (currDataSource == null) {
            return defaultDataSource;
        } else {
            return currDataSource;
        }
    }

    /**
     * 根据名称获取数据源
     *
     * @param dataSourceName
     * @return
     */
    private DataSource getDataSource(String dataSourceName) {
        return SpringApplicationContextHelper.getBean(dataSourceName, DataSource.class);
    }


    /**
     * 设置当前线程的数据源
     *
     * @param dataSource
     */
    public void setDataSource(DataSource dataSource) {
        if (dataSource != null) {
            this.dataSource.set(dataSource);
            if (this.defaultDataSource == null) {
                this.defaultDataSource = dataSource;
            }
        }
    }

    public void restoreDefaultDataSource() {
        log.info("设置数据源为初始数据源!");
        this.setDataSource(this.defaultDataSource);
    }

    /**
     * 切换到主数据源
     *
     * @return
     */
    public boolean switchToMaster() {
        DataSource masterDataSource = this.getDataSource(DataSourceConfig.MASTER_DATASOURCE_NAME);
        if (masterDataSource != null) {
            this.setDataSource(masterDataSource);
            return true;
        } else {
            this.restoreDefaultDataSource();
            log.error("未找到masterDataSource数据源配置,使用默认数据源");
            return false;
        }
    }

    /**
     * 切换到从数据源
     *
     * @return
     */
    public boolean switchToSlave() {
        DataSource dataSource = this.getDataSource(DataSourceConfig.SLAVE_DATASOURCE_NAME);
        if (dataSource != null) {
            this.setDataSource(dataSource);
            return true;
        } else {
            this.restoreDefaultDataSource();
            log.error("未找到slaveDataSource数据源配置，使用默认数据源");
            return false;
        }
    }

    /**
     * 切换数据源
     *
     * @param dataSourceName
     * @return
     */
    public boolean switchTo(String dataSourceName) {
        DataSource dataSource = this.getDataSource(dataSourceName);
        if (dataSource != null) {
            this.setDataSource(dataSource);
            return true;
        } else {
            this.restoreDefaultDataSource();
            log.error("未找到" + dataSourceName + "数据源配置，使用默认数据源");
            return false;
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return this.getDataSource().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return this.getDataSource().getConnection(username, password);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return this.getDataSource().unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return this.getDataSource().isWrapperFor(iface);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return this.getDataSource().getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        this.getDataSource().setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        this.getDataSource().setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return this.getDataSource().getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return this.getDataSource().getParentLogger();
    }
}
