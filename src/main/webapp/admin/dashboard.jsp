<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <!DOCTYPE html>
    <html>

    <head>
        <title>医院管理后台</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    </head>

    <body>
        <nav class="navbar navbar-dark bg-dark">
            <div class="container-fluid">
                <span class="navbar-brand mb-0 h1">飞马医院管理系统</span>
                <div class="d-flex text-white align-items-center">
                    <span class="me-3">Admin</span>
                    <a href="${pageContext.request.contextPath}/auth?action=logout"
                        class="btn btn-sm btn-outline-light">退出</a>
                </div>
            </div>
        </nav>

        <div class="container-fluid">
            <div class="row mt-4">
                <!-- 左侧菜单 -->
                <div class="col-md-2">
                    <div class="list-group">
                        <!-- 修改链接：指向 Servlet -->
                        <a href="${pageContext.request.contextPath}/admin?action=dashboard"
                            class="list-group-item list-group-item-action active">控制台</a>
                        <a href="${pageContext.request.contextPath}/admin?action=listDoctors"
                            class="list-group-item list-group-item-action">医生与排班管理</a>
                        <a href="${pageContext.request.contextPath}/admin?action=exportPdf" target="_blank"
                            class="list-group-item list-group-item-action">下载报表</a>
                    </div>
                </div>

                <!-- 右侧内容 -->
                <div class="col-md-10">
                    <div class="alert alert-info">
                        欢迎回来，管理员！现在是 <%= new java.util.Date() %>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="card text-white bg-primary mb-3">
                                <div class="card-header">总患者数</div>
                                <div class="card-body">
                                    <!-- 使用 EL -->
                                    <h5 class="card-title">${patientCount} 人</h5>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="card text-white bg-success mb-3">
                                <div class="card-header">今日预约</div>
                                <div class="card-body">
                                    <!-- 使用 EL -->
                                    <h5 class="card-title">${todayCount} 单</h5>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>

    </html>