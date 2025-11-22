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

    // 获取所有科室列表 (去重)
    public List<String> findAllDepartments() {
        // 这里 BasicDao 需要稍微扩充一下泛型支持，或者直接用 queryRunner
        // 为了简单，我们这里临时用 Object 接收
        // 实际建议在 BasicDao 加一个 queryColumnList 方法，这里先简化处理：
        // 如果这一步报错，可以先返回 null，后面教你补全
        return null;
    }
}