package com.pegasus.dao;

import com.pegasus.entity.Appointment;
import com.pegasus.utils.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class AppointmentDao extends BasicDao<Appointment> {
    public AppointmentDao() {
        super(Appointment.class);
    }

    // 插入预约记录 (事务内)
    public void insert(Connection conn, Appointment app) {
        String sql = "INSERT INTO t_appointment(id, patient_id, schedule_id, status) VALUES(?, ?, ?, ?)";
        update(conn, sql, app.getId(), app.getPatientId(), app.getScheduleId(), app.getStatus());
    }

    // 查询某患者的所有预约 (关联查询，为了显示医生名字)
    public List<Appointment> selectByPatient(Long patientId) {
        String sql = "SELECT a.*, d.name AS doctorName, d.department AS deptName, s.work_date AS workDate, s.shift_type AS shiftType " +
                     "FROM t_appointment a " +
                     "JOIN t_schedule s ON a.schedule_id = s.id " +
                     "JOIN t_doctor d ON s.doctor_id = d.id " +
                     "WHERE a.patient_id = ? ORDER BY a.create_time DESC";
        return queryMulti(sql, patientId);
    }

    // 检查是否重复预约 (同一天同一医生)
    public boolean checkRepeat(Long patientId, Long scheduleId) {
         Long count = (Long) queryScalar("SELECT count(*) FROM t_appointment WHERE patient_id=? AND schedule_id=? AND status=0",
                                          patientId, scheduleId);
         return count > 0;
    }

    // 统计各科室的预约数量
    public List<Map<String, Object>> countByDepartment() {
        String sql = "SELECT d.department AS deptName, COUNT(a.id) AS count " +
                     "FROM t_appointment a " +
                     "JOIN t_schedule s ON a.schedule_id = s.id " +
                     "JOIN t_doctor d ON s.doctor_id = d.id " +
                     "GROUP BY d.department";
        // MapListHandler 将每行结果转为一个 Map
        try {
            return queryRunner.query(DBUtil.getConnection(), sql, new org.apache.commons.dbutils.handlers.MapListHandler());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 统计今日预约数
    public Long countToday() {
        return (Long) queryScalar("SELECT count(*) FROM t_appointment WHERE DATE(create_time) = CURDATE()");
    }
}