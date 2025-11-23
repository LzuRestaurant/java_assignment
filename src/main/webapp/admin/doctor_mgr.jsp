<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>医生管理 - 飞马后台</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        </head>

        <body>
            <div class="container mt-4">
                <h3>医生管理 & 排班</h3>

                <!-- 消息提示 -->
                <c:if test="${not empty msg}">
                    <div class="alert alert-success">${msg}</div>
                </c:if>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger">${error}</div>
                </c:if>

                <!-- 工具栏 -->
                <div class="card mb-4">
                    <div class="card-body">
                        <h5 class="card-title">批量操作</h5>
                        <div class="row align-items-center">
                            <div class="col-md-6">
                                <!-- 文件上传表单 -->
                                <form action="${pageContext.request.contextPath}/admin" method="post"
                                    enctype="multipart/form-data" class="d-flex gap-2">
                                    <input type="hidden" name="action" value="importExcel">
                                    <input type="file" name="file" class="form-control" accept=".xlsx" required>
                                    <button type="submit" class="btn btn-success">导入Excel</button>
                                </form>
                            </div>
                            <div class="col-md-6 text-end">
                                <a href="${pageContext.request.contextPath}/files/template.xlsx"
                                    class="btn btn-outline-secondary btn-sm">下载模板</a>
                            </div>
                        </div>
                        <small class="text-muted">请上传 .xlsx 格式，列顺序：ID | 姓名 | 密码 | 科室 | 专长</small>
                    </div>
                </div>

                <!-- 数据列表 -->
                <table class="table table-bordered">
                    <thead class="table-light">
                        <tr>
                            <th>ID</th>
                            <th>姓名</th>
                            <th>科室</th>
                            <th>专长</th>
                            <th>状态</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${doctors}" var="d">
                            <tr>
                                <td>${d.id}</td>
                                <td>${d.name}</td>
                                <td>${d.department}</td>
                                <td>${d.specialty}</td>
                                <td>${d.isDeleted == 0 ? '正常' : '已删除'}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <a href="admin/dashboard.jsp" class="btn btn-link">返回控制台</a>
            </div>
        </body>

        </html>