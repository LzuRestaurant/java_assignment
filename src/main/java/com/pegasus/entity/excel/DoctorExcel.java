package com.pegasus.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class DoctorExcel {
    @ExcelProperty("医生ID")
    private Long id;

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("密码")
    private String password;

    @ExcelProperty("科室")
    private String department;

    @ExcelProperty("专长")
    private String specialty;
}