<%@ page import="com.warehouse.dto.CategoryDto" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Edit Category</title>
  <link href="/webjars/bootstrap/5.3.3/css/bootstrap.min.css" rel="stylesheet">
  <link href="/resources/static/css/style.css" rel="stylesheet">
</head>
<body>
<div class="container">
  <h1>Edit Category</h1>
  <form action="<%= request.getContextPath() %>/categories/edit" method="post">
    <input type="hidden" name="oldName" value="<%= ((CategoryDto) request.getAttribute("category")).getName() %>">
    <div class="mb-3">
      <label for="name" class="form-label">Name</label>
      <input type="text" class="form-control" id="name" name="name" value="<%= ((CategoryDto) request.getAttribute("category")).getName() %>" required>
    </div>
    <div class="mb-3">
      <label for="description" class="form-label">Description</label>
      <textarea class="form-control" id="description" name="description" required><%= ((CategoryDto) request.getAttribute("category")).getDescription() %></textarea>
    </div>
    <button type="submit" class="btn btn-primary">Update Category</button>
  </form>
</div>
<script src="/webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>
</body>
</html>
