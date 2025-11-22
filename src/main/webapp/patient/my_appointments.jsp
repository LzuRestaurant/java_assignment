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
                            <th>状态</th>
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
                                    <c:choose>
                                        <c:when test="${a.status == 0}"><span class="badge bg-success">预约成功</span>
                                        </c:when>
                                        <c:when test="${a.status == 1}"><span class="badge bg-secondary">已取消</span>
                                        </c:when>
                                        <c:otherwise>完成</c:otherwise>
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