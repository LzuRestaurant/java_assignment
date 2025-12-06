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

            #toast-container {
                position: fixed;
                top: 20px;
                left: 50%;
                transform: translateX(-50%);
                z-index: 1050;
                display: none;
            }

            .toast-bubble {
                background-color: rgba(0, 0, 0, 0.7);
                color: white;
                padding: 10px 20px;
                border-radius: 25px;
                font-size: 14px;
                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
                animation: fadeInOut 2s ease-in-out;
            }

            @keyframes fadeInOut {
                0% {
                    opacity: 0;
                    transform: translateY(-20px);
                }

                20% {
                    opacity: 1;
                    transform: translateY(0);
                }

                80% {
                    opacity: 1;
                    transform: translateY(0);
                }

                100% {
                    opacity: 0;
                    transform: translateY(-20px);
                }
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
                                        <!-- 修改 href 为 void(0) 防止页面跳动，添加 onclick 事件 -->
                                        <a href="javascript:void(0)" onclick="showToast()"
                                            class="text-decoration-none text-muted">忘记密码?</a>
                                    </div>
        </div>
        <!-- Toast 容器 -->
        <div id="toast-container">
            <div class="toast-bubble">暂不支持该功能</div>
        </div>

        <script>
            function showToast() {
                var toast = document.getElementById("toast-container");
                // 显示
                toast.style.display = "block";

                // 2秒后自动隐藏
                setTimeout(function () {
                    toast.style.display = "none";
                }, 2000); // 和 CSS 动画时间保持一致
            }
        </script>

    </body>

    </html>