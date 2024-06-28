<%@ page import="com.warehouse.dto.CategoryDto" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Product</title>
    <link href="/webjars/bootstrap/5.3.3/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resources/static/css/style.css" rel="stylesheet">
</head>
<body>
<div class="d-flex justify-content-center">
    <div class="cont-manage">
        <h1>Add Product</h1>
        <form action="<%= request.getContextPath() %>/products/add" method="post">
            <div class="mb-3">
                <label for="name" class="form-label">Name</label>
                <input type="text" class="form-control" id="name" name="name" required>
            </div>
            <div class="mb-3">
                <label for="description" class="form-label">Description</label>
                <textarea class="form-control" id="description" name="description" required></textarea>
            </div>
            <div class="mb-3">
                <label for="producer" class="form-label">Producer</label>
                <input type="text" class="form-control" id="producer" name="producer" required>
            </div>
            <div class="mb-3">
                <label for="amount" class="form-label">Amount</label>
                <input type="number" class="form-control" id="amount" name="amount" required>
            </div>
            <div class="mb-3">
                <label for="price" class="form-label">Price</label>
                <input type="number" step="0.01" class="form-control" id="price" name="price" required>
            </div>
            <div class="mb-3">
                <label for="category" class="form-label">Category</label>
                <select class="form-select" id="category" name="category" required>
                    <option value="" disabled selected>Select a category</option>
                    <%
                        List<CategoryDto> categories = (List<CategoryDto>) request.getAttribute("categories");
                        if (categories != null) {
                            for (CategoryDto category : categories) {
                    %>
                    <option value="<%= category.getName() %>"><%= category.getName() %></option>
                    <%
                            }
                        }
                    %>
                </select>
            </div>
            <div class="row">
                <div class="col text-start">
                    <button type="submit" class="btn btn-primary">Submit</button>
                </div>
                <div class="col text-end">
                    <a href="/products" class="btn btn-outline-secondary">Cancel</a>
                </div>
            </div>
        </form>
    </div>
</div>
<script src="/webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>
</body>
</html>
