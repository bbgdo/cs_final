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

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        Map<CategoryDto, Double> categories = new HashMap<>();
        String name_search = req.getParameter("name_search");

        if (name_search != null && !name_search.isEmpty()){
            Optional<CategoryDto> categoryOpt = categoryService.getById(name_search);
            if (categoryOpt.isPresent()) {
                CategoryDto category = categoryOpt.get();
                categories.put(category, categoryService.categoryValue(name_search));
            } else {
                req.getSession().setAttribute("errorMessage", "Category not found");
                resp.sendRedirect(req.getContextPath() + "/categories");
                return;
            }
        } else {
            List<CategoryDto> categoriesList = categoryService.getAll();
            for (CategoryDto category : categoriesList){
                categories.put(category, categoryService.categoryValue(category.getName()));
            }
        }

        req.setAttribute("categories", categories);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/category/categories.jsp");
        dispatcher.forward(req, resp);
    }



    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String name = req.getParameter("name");
        if (name != null && !name.isEmpty()) {
            categoryService.delete(name);
        }
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

}
