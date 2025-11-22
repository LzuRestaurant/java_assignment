package com.pegasus.web;

import com.pegasus.dao.AppointmentDao;
import com.pegasus.dao.DoctorDao;
import com.pegasus.dao.ScheduleDao;
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

    // 动作: preBook - 展示医生排班
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

    // 动作: confirmBook - 提交预约
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
}