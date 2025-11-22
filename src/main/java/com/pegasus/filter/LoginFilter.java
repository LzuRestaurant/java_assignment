package com.pegasus.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

// 拦截所有后台路径
@WebFilter(urlPatterns = {"/patient/*", "/admin/*"})
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();

        // 检查 Session 中是否有用户
        Object user = session.getAttribute("currentUser");
        String role = (String) session.getAttribute("role"); // "patient" or "admin"

        if (user == null || role == null) {
            // 未登录，重定向到登录页，并记录拦截前的 URL (可选)
            response.sendRedirect(request.getContextPath() + "/index.jsp?msg=Please login first");
        } else {
            // 权限分流 (防止患者访问管理员页面，反之亦然)
            String uri = request.getRequestURI();
            if (uri.contains("/admin/") && !"admin".equals(role)) {
                response.sendError(403, "Access Denied: You are not an admin.");
                return;
            }
            if (uri.contains("/patient/") && !"patient".equals(role)) {
                // 管理员一般也不看患者页面，不过可以放行或重定向
                // 这里简单处理，直接放行
            }

            chain.doFilter(req, resp);
        }
    }
}