<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>患者中心 - 飞马医院</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
        </head>

        <body class="bg-light">

            <!-- 导航栏 -->
            <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
                <div class="container">
                    <a class="navbar-brand" href="#"><i class="bi bi-hospital"></i> 飞马星球医院</a>
                    <div class="d-flex text-white">
                        <span class="me-3">欢迎, ${sessionScope.currentUser.name}</span>
                        <a href="${pageContext.request.contextPath}/auth?action=logout"
                            class="btn btn-sm btn-outline-light">退出</a>
                    </div>
                </div>
            </nav>

            <div class="container mt-5">
                <div class="row">
                    <!-- 功能卡片 1: 预约挂号 -->
                    <div class="col-md-4">
                        <div class="card text-center shadow-sm h-100">
                            <div class="card-body">
                                <h1 class="text-primary"><i class="bi bi-calendar-plus"></i></h1>
                                <h5 class="card-title">预约挂号</h5>
                                <p class="card-text">浏览科室与专家，在线预约。</p>
                                <!-- 点击跳转到 PatientServlet 的 listDoctors 方法 -->
                                <a href="${pageContext.request.contextPath}/patient?action=listDoctors"
                                    class="btn btn-primary">去挂号</a>
                            </div>
                        </div>
                    </div>

                    <!-- 功能卡片 2: 我的挂号 -->
                    <div class="col-md-4">
                        <div class="card text-center shadow-sm h-100">
                            <div class="card-body">
                                <h1 class="text-success"><i class="bi bi-card-list"></i></h1>
                                <h5 class="card-title">我的挂号</h5>
                                <p class="card-text">查看预约记录，取消预约。</p>
                                <a href="${pageContext.request.contextPath}/patient?action=myAppointments"
                                    class="btn btn-success">查看记录</a>
                            </div>
                        </div>
                    </div>

                    <!-- 功能卡片 3: 个人信息 -->
                    <div class="col-md-4">
                        <div class="card text-center shadow-sm h-100">
                            <div class="card-body">
                                <h1 class="text-info"><i class="bi bi-person-circle"></i></h1>
                                <h5 class="card-title">个人信息</h5>
                                <p class="card-text">查看或修改您的个人资料。</p>
                                <a href="${pageContext.request.contextPath}/patient?action=viewProfile" class="btn btn-info text-white">管理信息</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </body>

        </html>