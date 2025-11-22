package com.pegasus.entity;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class Appointment {
    private Long id;          // 预约号
    private Long patientId;
    private Long scheduleId;
    private Integer status;   // 0:已预约 1:已取消
    private LocalDateTime createTime;

    // 额外的字段，用于页面显示（不需要映射数据库表，查询时用 AS 别名填充）
    private String doctorName;
    private String deptName;
    private Date workDate;
    private String shiftType;
}