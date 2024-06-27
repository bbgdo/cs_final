<%@ page import="com.warehouse.dto.ProductDto" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Products</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }
        th {
            background-color: #f2f2f2;
        }
        form {
            display: inline;
        }
    </style>
    <script>
        function deleteProduct(name) {
            fetch('<%= request.getContextPath() %>/products?name=' + name, {
                method: 'DELETE'
            }).then(response => {
                if (response.ok) {
                    window.location.reload();
                } else {
                    alert('Failed to delete product');
                }
            });
        }
    </script>
</head>
<body>
<h2>Product List</h2>
<table>
    <thead>
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
            <button type="button" onclick="deleteProduct('<%= product.getName() %>')">Delete</button>
        </td>
    </tr>
    <%
            }
        }
    %>
    </tbody>
</table>
</body>
</html>
