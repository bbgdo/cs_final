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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/categories")
public class CategoryListController extends HttpServlet {
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
        List<CategoryDto> categoriesList = categoryService.getAll();
        Map<CategoryDto, Double> categories = new HashMap<>();
        for (CategoryDto category : categoriesList){
            categories.put(category, categoryService.categoryValue(category.getName()));
        }
        req.setAttribute("categories", categories);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/category/categories.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        if (name != null && !name.isEmpty()) {
            categoryService.delete(name);
        }
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

}
