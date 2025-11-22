package com.pegasus.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Schedule {
    private Long id;
    private Long doctorId;
    private Date workDate;    // java.sql.Date 会被自动映射
    private String shiftType; // Morning/Afternoon
    private Integer maxSlots;
    private Integer usedSlots;
    private Integer version;  // 乐观锁版本号
}