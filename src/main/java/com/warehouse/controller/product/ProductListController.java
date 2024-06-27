package com.warehouse.controller.product;

import com.warehouse.converter.CategoryConverter;
import com.warehouse.dao.impl.CategoryDaoImpl;
import com.warehouse.dto.CategoryDto;
import com.warehouse.dto.ProductDto;
import com.warehouse.service.CategoryService;
import com.warehouse.service.ProductService;
import com.warehouse.service.impl.CategoryServiceImpl;
import com.warehouse.service.impl.ProductServiceImpl;
import com.warehouse.converter.ProductConverter;
import com.warehouse.dao.impl.ProductDaoImpl;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/products")
public class ProductListController extends HttpServlet {
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
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        List<CategoryDto> categories = categoryService.getAll();
        List<ProductDto> products = new ArrayList<>();
        String name_search = req.getParameter("name_search");
        String category_search = req.getParameter("category_search");
        if (!(name_search == null) && !(name_search.isEmpty())){
            products.add(productService.getById(name_search).get());
        } else if (!(category_search == null) && !(category_search.isEmpty())) {
            products.addAll(productService.findByCategory(category_search));
        } else {
            products.addAll(productService.getAll());
        }
        req.setAttribute("products", products);
        req.setAttribute("categories", categories);
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String name = req.getParameter("name");
        int amount = Integer.parseInt(req.getParameter("amount"));

        if ("addAmount".equals(action)) {
            productService.addAmount(amount, name);
        } else if ("writeOff".equals(action)) {
            productService.writeOff(amount, name);
        }

        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
