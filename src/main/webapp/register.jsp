<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <!DOCTYPE html>
    <html>

    <head>
        <title>患者注册 - 飞马医院</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>

    <body class="bg-light">

        <div class="container mt-5">
            <div class="row justify-content-center">
                <div class="col-md-6">
                    <div class="card shadow">
                        <div class="card-header bg-primary text-white">
                            <h4>患者注册</h4>
                        </div>
                        <div class="card-body">
                            <!-- 错误提示信息 -->
                            <% if(request.getAttribute("msg") !=null) { %>
                                <div class="alert alert-danger">
                                    <%= request.getAttribute("msg") %>
                                </div>
                                <% } %>

                                    <!-- 提交到 AuthServlet，action=register -->
                                    <form action="${pageContext.request.contextPath}/auth" method="post">
                                        <input type="hidden" name="action" value="register">

                                        <div class="mb-3">
                                            <label>患者ID (10位数字)</label>
                                            <input type="number" name="id" class="form-control" required
                                                placeholder="例如：1000000002">
                                        </div>
                                        <div class="mb-3">
                                            <label>姓名</label>
                                            <input type="text" name="name" class="form-control" required>
                                        </div>
                                        <div class="mb-3">
                                            <label>密码</label>
                                            <input type="password" name="password" class="form-control" required>
                                        </div>
                                        <div class="mb-3">
                                            <label>身份证号</label>
                                            <input type="text" name="idCard" class="form-control" required>
                                        </div>
                                        <div class="mb-3">
                                            <label>手机号</label>
                                            <input type="text" name="phone" class="form-control" required>
                                        </div>
                                        <div class="mb-3">
                                            <label>性别</label>
                                            <select name="gender" class="form-select">
                                                <option value="M">男</option>
                                                <option value="F">女</option>
                                            </select>
                                        </div>
                                        <button type="submit" class="btn btn-success w-100">注册</button>
                                    </form>
                                    <div class="mt-3 text-center">
                                        <a href="index.jsp">已有账号？去登录</a>
                                    </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>

    </html>