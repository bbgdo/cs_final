<%@ page import="com.warehouse.dto.CategoryDto" %>
<%@ page import="java.util.Map" %>
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
                    <a class="nav-link text-white" aria-current="page" href="/">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-white" href="/products">Products</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-white" id="nav-active-link" id="nav-active-link" href="/categories">Categories</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div class="container text-start">
    <h1>Categories List</h1>
</div>
<div class="container-fluid cont-main">
<a href="/categories/add" class="btn btn-outline-primary">Add category</a>
</div>
<div class="container-fluid cont-main">
<table class="table table-hover">
    <thead class="table table-dark">
    <tr>
        <th style="width: 30%">Name</th>
        <th style="width: 30%">Description</th>
        <th style="width: 30%">Value</th>
        <th style="width: 10%">Actions</th>
    </tr>
    </thead>
    <tbody>
    <%
        Map<CategoryDto, Double> categories = (Map<CategoryDto, Double>) request.getAttribute("categories");
        if (categories != null) {
            for (Map.Entry<CategoryDto, Double> entry : categories.entrySet()) {
                CategoryDto category = entry.getKey();
                Double value = entry.getValue();
    %>
    <tr>
        <td><%= category.getName() %></td>
        <td><%= category.getDescription() %></td>
        <td><%= value %></td>
        <td class="text-end">
            <button class="btn btn-primary me-1" type="button" onclick="editCategory('<%= category.getName() %>')"><i class="bi bi-pencil-fill"></i></button>
            <button class="btn btn-danger me-1" type="button" onclick="deleteCategory('<%= category.getName() %>')"><i class="bi bi-trash-fill"></i></button>
        </td>
    </tr>
    <%
            }
        }
    %>
    </tbody>
</table>
</div>
<script src="/resources/static/js/categories.js"></script>
<script src="/webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>
</body>
</html>
