<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <style>
        form {
            width: 300px;
            margin: 0 auto;
            padding: 1em;
            border: 1px solid #ccc;
            border-radius: 1em;
        }
        div + div {
            margin-top: 1em;
        }
        label {
            display: inline-block;
            width: 90px;
            text-align: right;
        }
        input {
            font: 1em sans-serif;
            width: 200px;
            box-sizing: border-box;
            border: 1px solid #999;
        }
        input:focus {
            border-color: #000;
        }
        input[type="submit"] {
            padding: 0.7em;
            border-radius: 0.5em;
            background: #ccc;
            border: 1px solid #999;
            margin-left: .5em;
        }
        .error {
            color: red;
            text-align: center;
        }
    </style>
</head>
<body>
<h2>Login</h2>
<%--<form action="${pageContext.request.contextPath}/login" method="post">--%>
<form action="/login" method="post">
    <div>
        <label for="login">Login:</label>
        <input type="text" id="login" name="login" required>
    </div>
    <div>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>
    </div>
    <div>
        <input type="submit" value="Login">
    </div>
    <div class="error">
        <%
            String errorMessage = (String) request.getAttribute("errorMessage");
            if (errorMessage != null) {
                out.println(errorMessage);
            }
        %>
    </div>
</form>
</body>
</html>
