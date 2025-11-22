package com.pegasus.web;

import com.pegasus.dao.DoctorDao;
import com.pegasus.entity.Doctor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin")
public class AdminServlet extends BaseServlet {

    private final DoctorDao doctorDao = new DoctorDao();

    // 动作: listDoctors - 给 doctor_mgr.jsp 提供数据
    public void listDoctors(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Doctor> list = doctorDao.findAll();
        req.setAttribute("doctors", list);
        req.getRequestDispatcher("/admin/doctor_mgr.jsp").forward(req, resp);
    }

    // 动作: importExcel - 留给组员刘洪程实现
    public void importExcel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO: 使用 EasyExcel 解析上传的文件
        resp.getWriter().write("Excel import feature coming soon...");
    }

    // 动作: exportPdf - 留给组员陈海攀实现
    public void exportPdf(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO: 使用 OpenPDF 生成报表
        resp.getWriter().write("PDF export feature coming soon...");
    }
}