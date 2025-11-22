package com.pegasus.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {
    private Long id;
    private String name;
    private String password;
    private String department;
    private String specialty;
    private Integer isDeleted; // 0正常 1删除
}