<%@ page import="com.warehouse.dto.CategoryDto" %>
<%@ page import="com.warehouse.dto.ProductDto" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Edit Product</title>
  <link href="/webjars/bootstrap/5.3.3/css/bootstrap.min.css" rel="stylesheet">
  <link href="/resources/static/css/style.css" rel="stylesheet">
</head>
<body>
<div class="container">
  <h1>Edit Product</h1>
  <form action="<%= request.getContextPath() %>/products/edit" method="post">
    <input type="hidden" name="oldName" value="<%= ((ProductDto) request.getAttribute("product")).getName() %>">
    <div class="mb-3">
      <label for="name" class="form-label">Name</label>
      <input type="text" class="form-control" id="name" name="name" value="<%= ((ProductDto) request.getAttribute("product")).getName() %>" required>
    </div>
    <div class="mb-3">
      <label for="description" class="form-label">Description</label>
      <textarea class="form-control" id="description" name="description" required><%= ((ProductDto) request.getAttribute("product")).getDescription() %></textarea>
    </div>
    <div class="mb-3">
      <label for="producer" class="form-label">Producer</label>
      <input type="text" class="form-control" id="producer" name="producer" value="<%= ((ProductDto) request.getAttribute("product")).getProducer() %>" required>
    </div>
    <div class="mb-3">
      <label for="amount" class="form-label">Amount</label>
      <input type="number" class="form-control" id="amount" name="amount" value="<%= ((ProductDto) request.getAttribute("product")).getAmount() %>" required>
    </div>
    <div class="mb-3">
      <label for="price" class="form-label">Price</label>
      <input type="number" step="0.01" class="form-control" id="price" name="price" value="<%= ((ProductDto) request.getAttribute("product")).getPrice() %>" required>
    </div>
    <div class="mb-3">
      <label for="category" class="form-label">Category</label>
      <select class="form-select" id="category" name="category" required>
        <option value="" disabled>Select a category</option>
        <%
          List<CategoryDto> categories = (List<CategoryDto>) request.getAttribute("categories");
          if (categories != null) {
            for (CategoryDto category : categories) {
        %>
        <option value="<%= category.getName() %>" <%= category.getName().equals(((ProductDto) request.getAttribute("product")).getCategory()) ? "selected" : "" %>><%= category.getName() %></option>
        <%
            }
          }
        %>
      </select>
    </div>
    <button type="submit" class="btn btn-primary">Update Product</button>
  </form>
</div>
<script src="/webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>
</body>
</html>
