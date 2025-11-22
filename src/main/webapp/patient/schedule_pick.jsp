<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>选择时段 - 飞马医院</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        </head>

        <body>
            <div class="container mt-5">
                <div class="card">
                    <div class="card-header bg-primary text-white">
                        请选择预约时段
                    </div>
                    <div class="card-body">
                        <!-- 显示错误信息 -->
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger">${error}</div>
                        </c:if>

                        <table class="table table-bordered text-center">
                            <thead>
                                <tr>
                                    <th>日期</th>
                                    <th>时段</th>
                                    <th>剩余号源</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:if test="${empty schedules}">
                                    <tr>
                                        <td colspan="4">该医生暂无排班</td>
                                    </tr>
                                </c:if>
                                <c:forEach items="${schedules}" var="s">
                                    <tr>
                                        <td>${s.workDate}</td>
                                        <td>
                                            <span class="badge ${s.shiftType == 'Morning' ? 'bg-warning' : 'bg-info'}">
                                                ${s.shiftType == 'Morning' ? '上午' : '下午'}
                                            </span>
                                        </td>
                                        <td>
                                            <h5 class="text-danger">${s.maxSlots - s.usedSlots} / ${s.maxSlots}</h5>
                                        </td>
                                        <td>
                                            <c:if test="${s.usedSlots < s.maxSlots}">
                                                <form action="patient" method="post">
                                                    <input type="hidden" name="action" value="confirmBook">
                                                    <input type="hidden" name="doctorId" value="${doctorId}">
                                                    <!-- 用于失败回跳 -->
                                                    <input type="hidden" name="scheduleId" value="${s.id}">
                                                    <button type="submit" class="btn btn-primary btn-sm">确认挂号</button>
                                                </form>
                                            </c:if>
                                            <c:if test="${s.usedSlots >= s.maxSlots}">
                                                <button class="btn btn-secondary btn-sm" disabled>已满</button>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <a href="javascript:history.back()" class="btn btn-link">返回</a>
                    </div>
                </div>
            </div>
        </body>

        </html>