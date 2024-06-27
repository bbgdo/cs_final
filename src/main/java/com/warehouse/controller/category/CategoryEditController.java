package com.warehouse.controller.category;

import com.warehouse.converter.CategoryConverter;
import com.warehouse.dao.impl.CategoryDaoImpl;
import com.warehouse.dto.CategoryDto;
import com.warehouse.service.CategoryService;
import com.warehouse.service.impl.CategoryServiceImpl;
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

@WebServlet("/categories/edit")
public class CategoryEditController extends HttpServlet {
    private CategoryService categoryService;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

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

        String categoryName = req.getParameter("name");
        CategoryDto category = categoryService.getById(categoryName).get();

        req.setAttribute("category", category);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/category/categories-edit.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String oldName = req.getParameter("oldName");
        String name = req.getParameter("name");
        String description = req.getParameter("description");

        CategoryDto category = new CategoryDto(name, description);
        categoryService.update(category, oldName);

        resp.sendRedirect(req.getContextPath() + "/categories");
    }
}
