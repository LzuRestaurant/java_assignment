package com.pegasus.dao;

import com.pegasus.entity.Patient;

public class PatientDao extends BasicDao<Patient> {
    public PatientDao() {
        super(Patient.class);
    }

    public Patient selectById(Long id) {
        String sql = "SELECT id, name, password, id_card AS idCard, phone, gender, created_at AS createdAt FROM t_patient WHERE id = ?";
        return querySingle(sql, id);
    }

    public int insertPatient(Patient p) {
        String sql = "INSERT INTO t_patient(id, name, password, id_card, phone, gender) VALUES(?, ?, ?, ?, ?, ?)";
        return update(sql, p.getId(), p.getName(), p.getPassword(), p.getIdCard(), p.getPhone(), p.getGender());
    }

    public Long countById(Long id) {
         return (Long) queryScalar("SELECT count(*) FROM t_patient WHERE id = ?", id);
    }
}