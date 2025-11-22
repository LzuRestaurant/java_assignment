package com.pegasus.dao;
import com.pegasus.entity.Admin;
public class AdminDao extends BasicDao<Admin> {
    public AdminDao() { super(Admin.class); }

    public Admin login(String username, String password) {
        return querySingle("SELECT * FROM t_admin WHERE username=? AND password=?", username, password);
    }
}