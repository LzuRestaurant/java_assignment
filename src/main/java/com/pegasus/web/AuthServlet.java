package com.pegasus.web;

import com.pegasus.dao.AdminDao;
import com.pegasus.dao.PatientDao;
import com.pegasus.entity.Admin;
import com.pegasus.entity.Patient;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

// 访问路径: /auth?action=register 或 /auth?action=login
@WebServlet("/auth")
public class AuthServlet extends BaseServlet {

    private final PatientDao patientDao = new PatientDao();
    private final AdminDao adminDao = new AdminDao();

    // 处理注册
    public void register(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            // 1. 接收参数
            Long id = Long.parseLong(req.getParameter("id"));
            String name = req.getParameter("name");
            String password = req.getParameter("password");
            String idCard = req.getParameter("idCard");
            String phone = req.getParameter("phone");
            String gender = req.getParameter("gender");

            // 2. 简单校验 (防止重复注册)
            if (patientDao.countById(id) > 0) {
                req.setAttribute("msg", "该患者ID已存在！");
                req.getRequestDispatcher("/register.jsp").forward(req, resp);
                return;
            }

            // 3. 保存到数据库
            Patient p = new Patient(id, name, password, idCard, phone, gender, null);
            patientDao.insertPatient(p);

            // 4. 注册成功跳转登录页
            resp.sendRedirect(req.getContextPath() + "/index.jsp?msg=success");

        } catch (Exception e) {
            req.setAttribute("msg", "注册失败: " + e.getMessage());
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
        }
    }

    // 处理登录
    public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String idOrName = req.getParameter("id"); // 前端传来的 ID 或 用户名
        String password = req.getParameter("password");
        String loginType = req.getParameter("loginType"); // 新增参数：patient / admin

        if ("admin".equals(loginType)) {
            // 管理员登录
            Admin admin = adminDao.login(idOrName, password);
            if (admin != null) {
                req.getSession().setAttribute("currentUser", admin);
                req.getSession().setAttribute("role", "admin");
                resp.sendRedirect(req.getContextPath() + "/admin/dashboard.jsp");
            } else {
                req.setAttribute("msg", "管理员账号或密码错误");
                req.getRequestDispatcher("/index.jsp").forward(req, resp);
            }
        } else {
            // 患者登录 (保持原有逻辑，只是加了 role)
            try {
                Long id = Long.parseLong(idOrName);
                Patient patient = patientDao.selectById(id);
                if (patient != null && patient.getPassword().equals(password)) {
                    req.getSession().setAttribute("currentUser", patient);
                    req.getSession().setAttribute("role", "patient");
                    resp.sendRedirect(req.getContextPath() + "/patient/home.jsp");
                } else {
                    req.setAttribute("msg", "患者ID或密码错误");
                    req.getRequestDispatcher("/index.jsp").forward(req, resp);
                }
            } catch (NumberFormatException e) {
                req.setAttribute("msg", "患者ID必须是数字");
                req.getRequestDispatcher("/index.jsp").forward(req, resp);
            }
        }
    }

    // 处理注销
    public void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().invalidate();
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }
}