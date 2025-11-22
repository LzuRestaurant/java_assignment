package com.pegasus.dao;

import com.pegasus.entity.Schedule;
import java.sql.Connection;
import java.util.List;

public class ScheduleDao extends BasicDao<Schedule> {
    public ScheduleDao() {
        super(Schedule.class);
    }

    // 不要写 SELECT *，要把字段映射写清楚
    private static final String SELECT_ALL_FIELDS = "SELECT id, doctor_id AS doctorId, work_date AS workDate, " +
            "shift_type AS shiftType, max_slots AS maxSlots, " +
            "used_slots AS usedSlots, version FROM t_schedule ";

    // 查询某医生的未来排班
    public List<Schedule> findFutureSchedules(Long doctorId) {
        String sql = SELECT_ALL_FIELDS
                + "WHERE doctor_id = ? AND work_date >= CURDATE() ORDER BY work_date, shift_type";
        return queryMulti(sql, doctorId);
    }

    // 根据ID查询单条
    public Schedule selectById(Long id) {
        String sql = SELECT_ALL_FIELDS + "WHERE id = ?";
        return querySingle(sql, id);
    }

    /**
     * 核心方法：扣减库存 (使用乐观锁)
     * SQL 逻辑：只有当 version 等于我查出来的 version 时才更新，且更新后 version+1
     *
     * @return 更新行数，如果为 0 说明被别人抢先了
     */
    public int decreaseStock(Connection conn, Long id, int currentVersion) {
        String sql = "UPDATE t_schedule SET used_slots = used_slots + 1, version = version + 1 " +
                "WHERE id = ? AND version = ? AND used_slots < max_slots";
        return update(conn, sql, id, currentVersion);
    }
}