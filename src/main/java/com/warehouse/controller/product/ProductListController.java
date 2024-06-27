package com.warehouse.controller.product;

import com.warehouse.dto.ProductDto;
import com.warehouse.service.ProductService;
import com.warehouse.service.impl.ProductServiceImpl;
import com.warehouse.converter.ProductConverter;
import com.warehouse.dao.impl.ProductDaoImpl;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/products")
public class ProductListController extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            productService = new ProductServiceImpl(new ProductDaoImpl(), new ProductConverter());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       List<ProductDto> products = productService.getAll();
       req.setAttribute("products", products);
       RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/product/products.jsp");
       dispatcher.forward(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        if (name != null && !name.isEmpty()) {
            productService.delete(name);
        }
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

}
