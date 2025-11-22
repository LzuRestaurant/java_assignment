<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>医生列表 - 飞马医院</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        </head>

        <body>
            <div class="container mt-4">
                <h3 class="mb-3">专家列表</h3>

                <!-- 筛选区 (暂时只写个简单的链接) -->
                <div class="mb-3">
                    <a href="patient?action=listDoctors" class="btn btn-outline-primary">全部</a>
                    <a href="patient?action=listDoctors&dept=内科" class="btn btn-outline-secondary">内科</a>
                    <a href="patient?action=listDoctors&dept=外科" class="btn btn-outline-secondary">外科</a>
                    <a href="patient?action=listDoctors&dept=儿科" class="btn btn-outline-secondary">儿科</a>
                </div>

                <table class="table table-striped table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th>医生姓名</th>
                            <th>科室</th>
                            <th>专长</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        <!-- 使用 JSTL 遍历后端传来的 doctors 列表 -->
                        <c:forEach items="${doctors}" var="d">
                            <tr>
                                <td>${d.name}</td>
                                <td><span class="badge bg-info">${d.department}</span></td>
                                <td>${d.specialty}</td>
                                <td>
                                    <!-- 预约按钮，带上医生ID -->
                                    <a href="patient?action=preBook&doctorId=${d.id}"
                                        class="btn btn-sm btn-success">预约</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <div class="mt-3">
                    <a href="patient/home.jsp" class="btn btn-secondary">返回首页</a>
                </div>
            </div>
        </body>

        </html>