<%@ page import="com.warehouse.dto.ProductDto" %>
<%@ page import="com.warehouse.dto.CategoryDto" %>
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
                    <a class="nav-link text-white" id="nav-active-link" href="/products">Products</a>
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
<div class="container-fluid">
    <div class="row">
        <div class="col d-flex text-start">
            <a href="/products/add" class="btn btn-success">Add product</a>
        </div>
        <div class="col d-flex text-end">
            <button class="btn btn-dark dropdown-toggle" type="button" id="categoryDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                Categories
            </button>
            <ul class="dropdown-menu dropdown-menu-dark">
                <%
                    List<CategoryDto> categories = (List<CategoryDto>) request.getAttribute("categories");
                    if (categories != null) {
                        for (CategoryDto category : categories) {
                %>
                <li>
                    <a class="dropdown-item" href="<%= request.getContextPath() %>/products?category_search=<%= category.getName() %>">
                        <%= category.getName() %>
                    </a>
                </li>
                <%
                        }
                    }
                %>
            </ul>
            <form class="input-group me-2" action="/products" method="get">
                <input class="form-control" placeholder="name" type="text" id="name_search" name="name_search" required>
                <button type="submit" class="btn btn-outline-dark">Search</button>
            </form>
            <a href="/products" class="btn btn-dark">Reset</a>
        </div>
    </div>
</div>
<table class="table table-hover">
    <thead class="table table-dark">
    <tr>
        <th>Name</th>
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
        <td><%= product.getProducer() %></td>
        <td><%= product.getAmount() %></td>
        <td><%= product.getPrice() %></td>
        <td><%= product.getCategory() %></td>
        <td class="text-end">
            <button class="btn btn-dark" type="button" onclick="showDetails('<%= product.getName() %>', '<%= product.getDescription() %>', '<%= product.getAmount() %>', '<%= product.getPrice() %>')"><i class="bi bi-eye"></i></button>
            <button class="btn btn-primary" type="button" onclick="addAmount('<%= product.getName() %>')"><i class="bi bi-plus-circle"></i></button>
            <input type="number" class="form-control d-inline-block w-25" id="amount-<%= product.getName() %>" min="0">
            <button class="btn btn-primary" type="button" onclick="writeOff('<%= product.getName() %>')"><i class="bi bi-dash-circle"></i></button>
            <button class="btn btn-primary" type="button" onclick="editProduct('<%= product.getName() %>')"><i class="bi bi-pencil-fill"></i></button>
            <button class="btn btn-danger" type="button" onclick="deleteProduct('<%= product.getName() %>')"><i class="bi bi-trash-fill"></i></button>
        </td>
    </tr>
    <%
            }
        }
    %>
    </tbody>
</table>

<!-- Error Modal -->
<div class="modal fade" id="errorModal" tabindex="-1" aria-labelledby="errorModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="errorModalLabel">Error</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p id="errorMessage"></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">OK</button>
            </div>
        </div>
    </div>
</div>

<script src="/resources/static/js/products.js"></script>
<script src="/webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>
<script>
    document.addEventListener("DOMContentLoaded", function() {
        var errorMessage = '<%= session.getAttribute("errorMessage") %>';
        if (errorMessage && errorMessage !== "null") {
            document.getElementById('errorMessage').innerText = errorMessage;
            var errorModal = new bootstrap.Modal(document.getElementById('errorModal'));
            errorModal.show();
            <% session.removeAttribute("errorMessage"); %>
        }
    });
</script>
</body>
</html>
