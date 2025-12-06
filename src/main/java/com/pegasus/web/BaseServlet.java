package com.pegasus.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public abstract class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 解决 POST 请求中文乱码
        req.setCharacterEncoding("UTF-8");

        // 获取 action 参数
        String action = req.getParameter("action");
        if (action == null) {
            resp.sendError(400, "Missing action parameter");
            return;
        }

        try {
            // 利用反射调用子类方法: public void methodName(req, resp)
            Method method = this.getClass().getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            method.setAccessible(true);
            method.invoke(this, req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(500, "Execution Error: " + e.getMessage());
        }
    }
}