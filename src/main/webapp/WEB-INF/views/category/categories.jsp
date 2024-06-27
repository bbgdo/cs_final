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
<nav class="navbar navbar-expand-lg navbar-light bg-primary">
    <div class="container-fluid">
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item text-white">
                    <a class="nav-link" aria-current="page" href="/">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-white" href="/products">Products</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active text-white" href="/categories">Categories</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
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