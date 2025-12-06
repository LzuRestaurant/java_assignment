package com.pegasus.dao;

import com.pegasus.entity.Doctor;
import java.util.List;

public class DoctorDao extends BasicDao<Doctor> {
    public DoctorDao() {
        super(Doctor.class);
    }

    // 加上 AS 别名
    private static final String SELECT_DOCTOR = "SELECT id, name, password, department, specialty, is_deleted AS isDeleted FROM t_doctor ";

    // 查询所有医生
    public List<Doctor> findAll() {
        return queryMulti(SELECT_DOCTOR + "WHERE is_deleted = 0");
    }

    // 按科室查询
    public List<Doctor> findByDept(String dept) {
        return queryMulti(SELECT_DOCTOR + "WHERE is_deleted = 0 AND department = ?", dept);
    }

    public void insert(Doctor d) {
        String sql = "INSERT INTO t_doctor(id, name, password, department, specialty, is_deleted) VALUES(?,?,?,?,?,0)";
        update(sql, d.getId(), d.getName(), d.getPassword(), d.getDepartment(), d.getSpecialty());
    }

    // 获取所有科室列表
    public List<String> findAllDepartments() {
        // 为了简单，临时用 Object 接收
        return null;
    }
}