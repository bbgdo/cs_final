<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link href="/webjars/bootstrap/5.3.3/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resources/static/css/style.css" rel="stylesheet">
</head>
<body>
<%--<form action="${pageContext.request.contextPath}/login" method="post">--%>

<div class="container text-center" id="log-in-container">
    <h1 class="h3 mb-3 fw-normal text-center" id="body-head-h1-label">Please sign in</h1>
    <form action="/login" method="post">
        <div class="mb-3">
            <label class="form-label" for="login">Login:</label>
            <input class="form-control" type="text" id="login" name="login" required>
        </div>
        <div class="mb-3">
            <label class="form-label" for="password">Password:</label>
            <input class="form-control" type="password" id="password" name="password" required>
        </div>
        <div>
            <input class="btn btn-primary w-100 py-2" type="submit" value="Login">
        </div>
        <div class="error">
            <%
                String errorMessage = (String) request.getAttribute("errorMessage");
                if (errorMessage != null) {
                    out.println(errorMessage);
                }%>
        </div>
    </form>
</div>

<script src="/webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>
</body>
</html>
