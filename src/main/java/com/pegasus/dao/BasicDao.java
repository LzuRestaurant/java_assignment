package com.pegasus.dao;

import com.pegasus.utils.DBUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 泛型 DAO，封装了基本的 CRUD 操作
 *
 * @param <T> 具体的实体类类型
 */
public abstract class BasicDao<T> {
    protected final QueryRunner queryRunner = new QueryRunner();
    private final Class<T> type;

    public BasicDao(Class<T> type) {
        this.type = type;
    }

    // 通用增删改方法
    public int update(String sql, Object... params) {
        try (Connection conn = DBUtil.getConnection()) {
            return queryRunner.update(conn, sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 查询单行
    public T querySingle(String sql, Object... params) {
        try (Connection conn = DBUtil.getConnection()) {
            return queryRunner.query(conn, sql, new BeanHandler<>(type), params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 查询多行
    public List<T> queryMulti(String sql, Object... params) {
        try (Connection conn = DBUtil.getConnection()) {
            return queryRunner.query(conn, sql, new BeanListHandler<>(type), params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 查询单值 (例如 count(*))
    public Object queryScalar(String sql, Object... params) {
        try (Connection conn = DBUtil.getConnection()) {
            return queryRunner.query(conn, sql, new ScalarHandler<>(), params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 事务专用：传入外部 Connection 执行 Update
     * 注意：该方法不会关闭 Connection，由 Service 层控制事务提交/回滚
     */
    public int update(Connection conn, String sql, Object... params) {
        try {
            return queryRunner.update(conn, sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 事务专用：传入外部 Connection 执行 Insert 并返回生成的主键
     */
    public Long insertReturnKey(Connection conn, String sql, Object... params) {
        try {
            return queryRunner.insert(conn, sql, new ScalarHandler<Long>(), params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}