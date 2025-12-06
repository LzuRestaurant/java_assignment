package com.pegasus.service;

import com.pegasus.dao.AppointmentDao;
import com.pegasus.dao.ScheduleDao;
import com.pegasus.entity.Appointment;
import com.pegasus.entity.Schedule;
import com.pegasus.utils.DBUtil;
import org.apache.commons.lang3.RandomStringUtils;

import java.sql.Connection;
import java.sql.SQLException;
import com.pegasus.utils.AsyncTaskUtil;

public class AppointmentService {

    private final ScheduleDao scheduleDao = new ScheduleDao();
    private final AppointmentDao appointmentDao = new AppointmentDao();

    /**
     * 执行预约操作
     *
     * @return 预约成功生成的预约单号
     * @throws RuntimeException 如果预约失败，抛出异常消息
     */
    public Long makeAppointment(Long patientId, Long scheduleId) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            // 1. 开启事务
            conn.setAutoCommit(false);

            // 2. 检查排班是否存在
            Schedule schedule = scheduleDao.selectById(scheduleId);
            if (schedule == null)
                throw new RuntimeException("排班不存在");

            // 3. 检查是否已满
            if (schedule.getUsedSlots() >= schedule.getMaxSlots()) {
                throw new RuntimeException("号源已满");
            }

            // 4. 检查是否重复预约
            if (appointmentDao.checkRepeat(patientId, scheduleId)) {
                throw new RuntimeException("您已预约过该时段，不可重复预约");
            }

            // 5. 乐观锁扣减库存
            // 尝试更新，如果返回值是 0，说明刚才的一瞬间有人修改了数据（version变了），更新失败
            int rows = scheduleDao.decreaseStock(conn, scheduleId, schedule.getVersion());
            if (rows == 0) {
                throw new RuntimeException("预约失败，号源可能已被抢走，请重试");
            }

            // 6. 生成预约记录
            // 生成唯一的 12 位预约号
            Long appointmentId = Long.parseLong(RandomStringUtils.randomNumeric(12));

            Appointment app = new Appointment();
            app.setId(appointmentId);
            app.setPatientId(patientId);
            app.setScheduleId(scheduleId);
            app.setStatus(0); // 0: 已预约

            appointmentDao.insert(conn, app);

            // 7. 提交事务
            conn.commit();

            // 8. 使用多线程发送通知
            AsyncTaskUtil.sendSmsNotification("138xxxx", "您的预约已成功，预约号：" + appointmentId);

            return appointmentId;

        } catch (Exception e) {
            // 8. 回滚事务
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw new RuntimeException(e.getMessage());
        } finally {
            // 9. 关闭连接
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}