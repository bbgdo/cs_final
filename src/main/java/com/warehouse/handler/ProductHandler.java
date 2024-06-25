package com.warehouse.handler;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.warehouse.config.ThymeleafConfig;
import com.warehouse.converter.ProductConverter;
import com.warehouse.dao.impl.ProductDaoImpl;
import com.warehouse.dto.ProductDto;
import com.warehouse.entity.Product;
import com.warehouse.service.ProductService;
import com.warehouse.service.impl.ProductServiceImpl;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;

public class ProductHandler implements HttpHandler {


    private final ProductServiceImpl productService;

    {
        try {
            productService = new ProductServiceImpl(new ProductDaoImpl(), new ProductConverter());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                handleProductList(exchange);
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
    }

    private void handleGoodRequest(HttpExchange exchange, String method, Long id) throws IOException {
        switch (method) {
            case "GET":
                handleGetGood(exchange, id);
                break;
            case "POST":
                handleUpdateGood(exchange, id);
                break;
            case "DELETE":
                handleDeleteGood(exchange, id);
                break;
            default:
                exchange.sendResponseHeaders(405, -1);
                exchange.close();
                break;
        }
    }

    private void handleProductList(HttpExchange exchange) throws IOException {
        List<ProductDto> products = productService.getAll();
        Context context = new Context();
        context.setVariable("products", products );

        String response = ThymeleafConfig.getTemplateEngine().process("products", context);
        exchange.getResponseHeaders().set("Content-Type", "text/html");
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void handleGetGood(HttpExchange exchange, Long id) throws IOException {
        /*try {
            List<ProductDto> good = productService.getAll();
            if (good != null) {
                String response = objectMapper.writeValueAsString(good);
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                exchange.sendResponseHeaders(404, -1);
                exchange.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(500, -1);
            exchange.close();
        }*/
    }

    private void handleUpdateGood(HttpExchange exchange, Long id) throws IOException {
        /*try {
            JsonNode node = objectMapper.readTree(exchange.getRequestBody());
            Good good = objectMapper.treeToValue(node, Good.class);

            productService.update(ProductDto);
            exchange.sendResponseHeaders(204, -1);
            exchange.close();
        } catch (SQLException e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(500, -1);
            exchange.close();
        }*/
    }

    private void handleDeleteGood(HttpExchange exchange, Long id) throws IOException {
        /*try {
            int rowsAffected = ProductDao.delete(id);
            if (rowsAffected > 0) {
                exchange.sendResponseHeaders(204, -1);
            } else {
                exchange.sendResponseHeaders(404, -1);
            }
            exchange.close();
        } catch (SQLException e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(500, -1);
            exchange.close();
        }*/
    }

    private void handleGoodCreation(HttpExchange exchange, String method) throws IOException {

        /*try {


            ProductDao.create(good);
            String response = "Created good:" + good.getName(); //I return name here because I have autoincrement on ID(no sense to do one more query)
            exchange.sendResponseHeaders(201, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (SQLException e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(500, -1);
            exchange.close();
        }*/
    }
}
