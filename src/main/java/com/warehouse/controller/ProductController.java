package com.warehouse.controller;

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
public class ProductController extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            productService = new ProductServiceImpl(new ProductDaoImpl(), new ProductConverter());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       List<ProductDto> products = productService.getAll();
       req.setAttribute("products", products);
       req.getRequestDispatcher("products.jsp").forward(req, resp);
       //RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/products.jsp");
       //dispatcher.forward(req, resp); спробуй ось ці два рядка замість останнього якщо ен спрацює
    }


}
