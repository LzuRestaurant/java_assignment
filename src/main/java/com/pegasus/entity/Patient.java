package com.pegasus.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
// import java.time.LocalDateTime; // 删除这个
import java.util.Date;           // 换成这个

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    private Long id;
    private String name;
    private String password;
    private String idCard;
    private String phone;
    private String gender;
    private Date createdAt; // 改为 java.util.Date (它能兼容 java.sql.Timestamp)
}