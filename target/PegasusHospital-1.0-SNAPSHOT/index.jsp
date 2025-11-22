<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <!DOCTYPE html>
    <html>

    <head>
        <title>飞马星球医院 - 登录</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background: linear-gradient(120deg, #84fab0 0%, #8fd3f4 100%);
                height: 100vh;
                display: flex;
                align-items: center;
                justify-content: center;
            }

            .login-card {
                width: 100%;
                max-width: 400px;
                background: white;
                padding: 30px;
                border-radius: 15px;
                box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
            }
        </style>
    </head>

    <body>

        <div class="login-card">
            <h3 class="text-center mb-4">飞马医院 · 登录</h3>

            <%-- 显示错误信息 --%>
                <% if(request.getAttribute("msg") !=null) { %>
                    <div class="alert alert-danger">
                        <%= request.getAttribute("msg") %>
                    </div>
                    <% } %>
                        <%-- 显示注册成功信息 --%>
                            <% if("success".equals(request.getParameter("msg"))) { %>
                                <div class="alert alert-success">注册成功，请登录</div>
                                <% } %>

                                    <form action="${pageContext.request.contextPath}/auth" method="post">
                                        <input type="hidden" name="action" value="login">

                                        <div class="mb-3">
                                            <label class="form-label">身份</label>
                                            <div class="d-flex gap-3">
                                                <div class="form-check">
                                                    <input class="form-check-input" type="radio" name="loginType"
                                                        value="patient" checked>
                                                    <label class="form-check-label">我是患者</label>
                                                </div>
                                                <div class="form-check">
                                                    <input class="form-check-input" type="radio" name="loginType"
                                                        value="admin">
                                                    <label class="form-check-label">我是管理员</label>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">用户ID</label>
                                            <input type="string" name="id" class="form-control" placeholder="账号ID"
                                                required>
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">密码</label>
                                            <input type="password" name="password" class="form-control"
                                                placeholder="password" required>
                                        </div>
                                        <button type="submit" class="btn btn-primary w-100 py-2">登 录</button>
                                    </form>

                                    <div class="mt-3 text-center d-flex justify-content-between">
                                        <a href="register.jsp" class="text-decoration-none">注册新账号</a>
                                        <a href="#" class="text-decoration-none text-muted">忘记密码?</a>
                                    </div>
        </div>

    </body>

    </html>