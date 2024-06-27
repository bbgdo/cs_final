<%@ page import="java.util.List" %>
<%@ page import="com.warehouse.dto.CategoryDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Categories</title>
    <link href="/webjars/bootstrap/5.3.3/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resources/static/css/style.css" rel="stylesheet">
</head>
<body>
<div class="container text-center">
    <h1>Categories List</h1>
</div>
<a href="/categories/add" class="btn btn-success">Add category</a>
<table class="table table-hover">
    <thead class="table table-dark">
    <tr>
        <th>Name</th>
        <th>Description</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <%
        List<CategoryDto> categories = (List<CategoryDto>) request.getAttribute("categories");
        if (categories != null) {
            for (CategoryDto category : categories) {
    %>
    <tr>
        <td><%= category.getName() %></td>
        <td><%= category.getDescription() %></td>
        <td>
            <button class="btn btn-info" type="button" onclick="editCategory('<%= category.getName() %>')"><i class="bi bi-pencil-fill"></i></button>
            <button class="btn btn-danger" type="button" onclick="deleteCategory('<%= category.getName() %>')"><i class="bi bi-trash-fill"></i></button>
        </td>
    </tr>
    <%
            }
        }
    %>
    </tbody>
</table>
<script src="/resources/static/js/categories.js"></script>
<script src="/webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>
</body>
</html>
