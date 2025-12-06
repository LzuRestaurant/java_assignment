<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>我的预约 - 飞马医院</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        </head>

        <body>
            <div class="container mt-5">
                <h3>我的挂号记录</h3>

                <c:if test="${not empty msg}">
                    <div class="alert alert-success">${msg}</div>
                </c:if>

                <table class="table table-hover mt-3">
                    <thead>
                        <tr>
                            <th>预约号</th>
                            <th>医生</th>
                            <th>科室</th>
                            <th>就诊时间</th>
                            <th>状态</th> <!-- 这里把状态和操作合并显示 -->
                            <th>创建时间</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${list}" var="a">
                            <tr>
                                <td>${a.id}</td>
                                <td>${a.doctorName}</td>
                                <td>${a.deptName}</td>
                                <td>${a.workDate} (${a.shiftType})</td>
                                <td>
                                    <!-- 修复后的逻辑 -->
                                    <c:choose>
                                        <%-- 情况1：预约成功 (status=0) --%>
                                            <c:when test="${a.status == 0}">
                                                <span class="badge bg-success">预约成功</span>
                                                <a href="patient?action=cancelAppointment&id=${a.id}"
                                                    class="btn btn-sm btn-outline-danger ms-2"
                                                    style="padding: 0px 5px; font-size: 12px;"
                                                    onclick="return confirm('确定取消吗？')">取消</a>
                                            </c:when>

                                            <%-- 情况2：已取消 (status=1) --%>
                                                <c:when test="${a.status == 1}">
                                                    <span class="badge bg-secondary">已取消</span>
                                                </c:when>

                                                <%-- 其他情况：已完成 --%>
                                                    <c:otherwise>
                                                        <span class="badge bg-primary">已完成</span>
                                                    </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${a.createTime}</td>
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