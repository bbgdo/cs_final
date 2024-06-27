<%@ page import="com.warehouse.dto.ProductDto" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Products</title>
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
                <li class="nav-item">
                    <a class="nav-link text-white" aria-current="page" href="/">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active text-white" href="/products">Products</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-white" href="/categories">Categories</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div class="container text-center">
<h1>Product List</h1>
</div>
<a href="/products/add" class="btn btn-success">Add product</a>
<table class="table table-hover">
    <thead class="table table-dark">
    <tr>
        <th>Name</th>
        <th>Description</th>
        <th>Producer</th>
        <th>Amount</th>
        <th>Price</th>
        <th>Category</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <%
        List<ProductDto> products = (List<ProductDto>) request.getAttribute("products");
        if (products != null) {
            for (ProductDto product : products) {
    %>
    <tr>
        <td><%= product.getName() %></td>
        <td><%= product.getDescription() %></td>
        <td><%= product.getProducer() %></td>
        <td><%= product.getAmount() %></td>
        <td><%= product.getPrice() %></td>
        <td><%= product.getCategory() %></td>
        <td>
            <button class="btn btn-info" type="button" onclick="editProduct('<%= product.getName() %>')"><i class="bi bi-pencil-fill"></i></button>
            <button class="btn btn-danger" type="button" onclick="deleteProduct('<%= product.getName() %>')"><i class="bi bi-trash-fill"></i></button>
        </td>
    </tr>
    <%
            }
        }
    %>
    </tbody>
</table>
<script src="/resources/static/js/products.js"></script>
<script src="/webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>
</body>
</html>
