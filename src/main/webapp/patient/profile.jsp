<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>个人信息 - 飞马医院</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        </head>

        <body>
            <div class="container mt-5">
                <div class="row justify-content-center">
                    <div class="col-md-8">
                        <div class="card shadow">
                            <div class="card-header bg-info text-white">
                                <h4>管理个人信息</h4>
                            </div>
                            <div class="card-body">
                                <c:if test="${not empty msg}">
                                    <div class="alert alert-success">${msg}</div>
                                </c:if>

                                <form action="${pageContext.request.contextPath}/patient" method="post">
                                    <input type="hidden" name="action" value="updateProfile">

                                    <!-- 不可修改字段 -->
                                    <div class="row mb-3">
                                        <div class="col">
                                            <label class="form-label">患者ID (不可修改)</label>
                                            <input type="text" class="form-control" value="${p.id}" disabled readonly>
                                        </div>
                                        <div class="col">
                                            <label class="form-label">身份证号 (不可修改)</label>
                                            <input type="text" class="form-control" value="${p.idCard}" disabled
                                                readonly>
                                        </div>
                                    </div>

                                    <!-- 可修改字段 -->
                                    <!-- 姓名 -->
                                    <div class="mb-3">
                                        <label class="form-label">姓名</label>
                                        <input type="text" name="name" class="form-control" value="${p.name}"
                                            maxlength="20" required>
                                    </div>
                                    <!-- 密码 -->
                                    <div class="mb-3">
                                        <label class="form-label">密码</label>
                                        <input type="text" name="password" class="form-control" value="${p.password}"
                                            minlength="4" maxlength="20" required>
                                    </div>
                                    <!-- 手机号 -->
                                    <div class="mb-3">
                                        <label class="form-label">手机号</label>
                                        <input type="text" name="phone" class="form-control" value="${p.phone}"
                                            pattern="^1[3-9]\d{9}$" title="请输入有效的11位手机号" required>
                                    </div>
                                    <div class="mb-3">
                                        <label class="form-label">性别</label>
                                        <select name="gender" class="form-select">
                                            <option value="M" ${p.gender=='M' ? 'selected' : '' }>男</option>
                                            <option value="F" ${p.gender=='F' ? 'selected' : '' }>女</option>
                                        </select>
                                    </div>

                                    <div class="d-flex justify-content-between">
                                        <a href="${pageContext.request.contextPath}/patient/home.jsp"
                                            class="btn btn-secondary">返回首页</a>
                                        <button type="submit" class="btn btn-primary">保存修改</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </body>

        </html>