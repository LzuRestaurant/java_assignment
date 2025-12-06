package com.pegasus.web;

import com.pegasus.dao.AppointmentDao;
import com.pegasus.dao.DoctorDao;
import com.pegasus.dao.ScheduleDao;
import com.pegasus.dao.PatientDao;
import com.pegasus.entity.Appointment;
import com.pegasus.entity.Doctor;
import com.pegasus.entity.Patient;
import com.pegasus.entity.Schedule;
import com.pegasus.service.AppointmentService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

// 拦截 /patient 路径的所有请求
@WebServlet("/patient")
public class PatientServlet extends BaseServlet {

    private final DoctorDao doctorDao = new DoctorDao();
    private final ScheduleDao scheduleDao = new ScheduleDao();
    private final AppointmentService appointmentService = new AppointmentService(); // 引入 Service
    private final AppointmentDao appointmentDao = new AppointmentDao();
    private final PatientDao patientDao = new PatientDao();

    // 对应 /patient?action=listDoctors
    public void listDoctors(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 获取筛选参数 (科室)
        String dept = req.getParameter("dept");
        List<Doctor> doctors;

        // 2. 查询数据
        if (dept != null && !dept.isEmpty()) {
            doctors = doctorDao.findByDept(dept);
        } else {
            doctors = doctorDao.findAll();
        }

        // 3. 存入 Request
        req.setAttribute("doctors", doctors);

        // 4. 转发到 JSP
        req.getRequestDispatcher("/patient/doctors.jsp").forward(req, resp);
    }

    // 对应 /patient?action=myAppointments
    public void myAppointments(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Patient current = (Patient) req.getSession().getAttribute("currentUser");
        List<Appointment> list = appointmentDao.selectByPatient(current.getId());
        req.setAttribute("list", list);
        req.getRequestDispatcher("/patient/my_appointments.jsp").forward(req, resp);
    }

    // 展示医生排班
    public void preBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String doctorIdStr = req.getParameter("doctorId");
        if (doctorIdStr == null) {
            resp.sendRedirect("patient?action=listDoctors");
            return;
        }

        Long doctorId = Long.parseLong(doctorIdStr);
        // 查询该医生的排班
        List<Schedule> schedules = scheduleDao.findFutureSchedules(doctorId);

        req.setAttribute("schedules", schedules);
        req.setAttribute("doctorId", doctorId);
        req.getRequestDispatcher("/patient/schedule_pick.jsp").forward(req, resp);
    }

    // 提交预约
    public void confirmBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // 获取当前登录用户
            Patient current = (Patient) req.getSession().getAttribute("currentUser");
            if (current == null) {
                resp.sendRedirect(req.getContextPath() + "/index.jsp");
                return;
            }

            Long scheduleId = Long.parseLong(req.getParameter("scheduleId"));

            // 调用 Service 执行核心逻辑
            Long appointmentId = appointmentService.makeAppointment(current.getId(), scheduleId);

            // 成功跳转
            req.setAttribute("msg", "预约成功！您的预约号是：" + appointmentId);
            // 转到“我的预约”页面
            myAppointments(req, resp);

        } catch (Exception e) {
            // 失败回退
            req.setAttribute("error", e.getMessage());
            // 重新加载排班页
            preBook(req, resp);
        }
    }

    public void cancelAppointment(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idStr = req.getParameter("id"); // 预约单号
        if (idStr != null) {
            Long appId = Long.parseLong(idStr);
            String sql = "UPDATE t_appointment SET status = 1 WHERE id = ?";
            new com.pegasus.dao.AppointmentDao().update(sql, appId);
        }
        // 刷新列表
        resp.sendRedirect("patient?action=myAppointments");
    }

    // 跳转到个人信息页
    public void viewProfile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 直接转发到 JSP，因为数据都在 Session (currentUser) 里了
        Patient sessionUser = (Patient) req.getSession().getAttribute("currentUser");
        Patient p = patientDao.selectById(sessionUser.getId());
        req.setAttribute("p", p);
        req.getRequestDispatcher("/patient/profile.jsp").forward(req, resp);
    }

    // 提交修改
    public void updateProfile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Patient user = (Patient) req.getSession().getAttribute("currentUser");

        // 1. 接收参数
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String phone = req.getParameter("phone");
        String gender = req.getParameter("gender");

        // 2. 更新对象
        user.setName(name);
        user.setPassword(password);
        user.setPhone(phone);
        user.setGender(gender);

        // 3. 更新数据库
        patientDao.updateInfo(user);

        // 4. 更新 Session (否则页面显示的还是旧名字)
        req.getSession().setAttribute("currentUser", user);

        // 5. 提示并跳转
        req.setAttribute("msg", "信息修改成功！");
        req.setAttribute("p", user);
        req.getRequestDispatcher("/patient/profile.jsp").forward(req, resp);
    }
}