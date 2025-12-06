package com.pegasus.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.pegasus.dao.DoctorDao;
import com.pegasus.entity.Doctor;
import com.pegasus.entity.excel.DoctorExcel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {

    public static void readDoctorExcel(InputStream is) {
        EasyExcel.read(is, DoctorExcel.class, new ReadListener<DoctorExcel>() {
            private static final int BATCH_COUNT = 20;
            private List<Doctor> cacheList = new ArrayList<>();
            private DoctorDao doctorDao = new DoctorDao();
            private int rowIndex = 1; // 记录行号，方便报错

            @Override
            public void invoke(DoctorExcel data, AnalysisContext context) {
                rowIndex++; // Excel第一行通常是表头，数据从第2行开始

                // 【核心修改】调用校验逻辑
                try {
                    ValidatorUtil.validateDoctor(
                            data.getId(),
                            data.getName(),
                            data.getPassword(),
                            data.getDepartment(),
                            data.getSpecialty());
                } catch (IllegalArgumentException e) {
                    // 捕获校验错误，包装成 RuntimeException 抛出，中断导入
                    throw new RuntimeException("导入失败 (第 " + rowIndex + " 行): " + e.getMessage());
                }

                // 校验通过，转换对象
                Doctor d = new Doctor();
                d.setId(data.getId());
                d.setName(data.getName());
                d.setPassword(data.getPassword());
                d.setDepartment(data.getDepartment());
                d.setSpecialty(data.getSpecialty());
                d.setIsDeleted(0);

                cacheList.add(d);
                if (cacheList.size() >= BATCH_COUNT) {
                    saveData();
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                saveData();
            }

            private void saveData() {
                for (Doctor d : cacheList) {
                    try {
                        doctorDao.insert(d);
                    } catch (Exception e) {
                        // 可能是主键冲突等数据库错误
                        throw new RuntimeException("数据库写入失败: 医生ID " + d.getId() + " 可能已存在");
                    }
                }
                cacheList.clear();
            }
        }).sheet().doRead();
    }
}