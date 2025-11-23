package com.pegasus.web;

import com.pegasus.dao.DoctorDao;
import com.pegasus.entity.Doctor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.pegasus.utils.ExcelUtil;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;

import com.pegasus.utils.PdfUtil;
import com.pegasus.dao.AppointmentDao;
import java.util.Map;

@WebServlet("/admin")
@MultipartConfig
public class AdminServlet extends BaseServlet {

    private final DoctorDao doctorDao = new DoctorDao();
    private final AppointmentDao appointmentDao = new AppointmentDao();

    // 动作: listDoctors - 给 doctor_mgr.jsp 提供数据
    public void listDoctors(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Doctor> list = doctorDao.findAll();
        req.setAttribute("doctors", list);
        req.getRequestDispatcher("/admin/doctor_mgr.jsp").forward(req, resp);
    }

    /**
     * 处理 Excel 导入请求
     * URL: /admin?action=importExcel
     * Method: POST
     * Enctype: multipart/form-data
     */
    public void importExcel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // 1. 获取上传的文件 Part (HTML input name="file")
            Part filePart = req.getPart("file");
            if (filePart == null || filePart.getSize() == 0) {
                req.setAttribute("msg", "请选择文件！");
                listDoctors(req, resp); // 回到列表页
                return;
            }

            // 2. 调用工具类读取并入库
            ExcelUtil.readDoctorExcel(filePart.getInputStream());

            // 3. 成功反馈
            req.setAttribute("msg", "批量导入成功！");

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "导入失败: " + e.getMessage());
        }

        // 4. 刷新列表
        listDoctors(req, resp);
    }

    /**
     * 导出 PDF 报表
     * URL: /admin?action=exportPdf
     */
    public void exportPdf(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 获取统计数据
        List<Map<String, Object>> statsData = appointmentDao.countByDepartment();

        // 2. 设置响应头 (告诉浏览器下载文件)
        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "attachment; filename=monthly_report.pdf");

        // 3. 生成 PDF 并写入响应流
        PdfUtil.exportStatsPdf(resp.getOutputStream(), statsData);
    }
}