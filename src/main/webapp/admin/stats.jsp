<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>统计报表 - 飞马后台</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5 text-center">
    <div class="card shadow">
        <div class="card-header bg-primary text-white">
            <h4>月度统计报表中心</h4>
        </div>
        <div class="card-body">
            <p class="lead">点击下方按钮，生成本月各科室预约量统计报告 (PDF格式)</p>

            <form action="${pageContext.request.contextPath}/admin" method="get" target="_blank">
                <input type="hidden" name="action" value="exportPdf">
                <button type="submit" class="btn btn-lg btn-danger">
                    <i class="bi bi-file-pdf"></i> 下载 PDF 报表
                </button>
            </form>

        </div>
        <div class="card-footer text-muted">
            数据来源：飞马星球医院实时数据库
        </div>
    </div>

    <div class="mt-4">
        <a href="admin/dashboard.jsp" class="btn btn-secondary">返回控制台</a>
    </div>
</div>
</body>
</html>