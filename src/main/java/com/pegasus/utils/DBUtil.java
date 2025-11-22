package com.pegasus.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {
    private static HikariDataSource dataSource;

    static {
        try (InputStream is = DBUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            // 如果找不到配置文件，抛出明确异常
            if (is == null) {
                throw new RuntimeException("Cannot find db.properties in classpath!");
            }

            Properties props = new Properties();
            props.load(is);

            HikariConfig config = new HikariConfig();
            config.setDriverClassName(props.getProperty("driverClassName"));
            config.setJdbcUrl(props.getProperty("jdbcUrl"));
            config.setUsername(props.getProperty("username"));
            config.setPassword(props.getProperty("password"));
            config.setMaximumPoolSize(Integer.parseInt(props.getProperty("maximumPoolSize", "10")));

            dataSource = new HikariDataSource(config);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("数据库连接池初始化失败", e);
        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}