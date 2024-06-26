package com.warehouse.controller;

import com.warehouse.dto.ProductDto;
import com.warehouse.service.ProductService;
import com.warehouse.service.impl.ProductServiceImpl;
import com.warehouse.converter.ProductConverter;
import com.warehouse.dao.impl.ProductDaoImpl;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/products/")
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
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            List<ProductDto> products = productService.getAll();
            req.setAttribute("products", products);
            req.getRequestDispatcher("/WEB-INF/views/products.jsp").forward(req, resp);
        } else {
            String name = pathInfo.substring(1);
            Optional<ProductDto> product = productService.getById(name);
            if (product.isPresent()) {
                req.setAttribute("product", product.get());
                req.getRequestDispatcher("/WEB-INF/views/product.jsp").forward(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
            }
        }
    }

}
