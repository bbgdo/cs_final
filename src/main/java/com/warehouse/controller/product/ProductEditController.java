package com.warehouse.controller.product;

import com.warehouse.converter.CategoryConverter;
import com.warehouse.converter.ProductConverter;
import com.warehouse.dao.impl.CategoryDaoImpl;
import com.warehouse.dao.impl.ProductDaoImpl;
import com.warehouse.dto.CategoryDto;
import com.warehouse.dto.ProductDto;
import com.warehouse.service.CategoryService;
import com.warehouse.service.ProductService;
import com.warehouse.service.impl.CategoryServiceImpl;
import com.warehouse.service.impl.ProductServiceImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/products/edit")
public class ProductEditController extends HttpServlet {
    private ProductService productService;
    private CategoryService categoryService;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            productService = new ProductServiceImpl(new ProductDaoImpl(), new ProductConverter());
            categoryService = new CategoryServiceImpl(new CategoryDaoImpl(), new CategoryConverter());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productName = req.getParameter("name");
        ProductDto product = productService.getById(productName).get();
        List<CategoryDto> categories = categoryService.getAll();

        req.setAttribute("product", product);
        req.setAttribute("categories", categories);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/product/products-edit.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String oldName = req.getParameter("oldName");
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String producer = req.getParameter("producer");
        int amount = Integer.parseInt(req.getParameter("amount"));
        double price = Double.parseDouble(req.getParameter("price"));
        String category = req.getParameter("category");

        ProductDto product = new ProductDto(name, description, producer, amount, price, category);
        productService.update(product, oldName);

        resp.sendRedirect(req.getContextPath() + "/products");
    }
}
