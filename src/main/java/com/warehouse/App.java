package com.warehouse;

import com.sun.net.httpserver.HttpServer;
import com.warehouse.handler.ProductHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class App {
    public static void main(String[] args)  throws IOException {
        HttpServer httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(8080), 0);

        httpServer.createContext("/products", new ProductHandler());
        //httpServer.createContext("/api/good/{id}", new GoodHandler()).setAuthenticator(new Auth());

        httpServer.setExecutor(null);
        httpServer.start();
        System.out.println("Server started on port 8080");
    }
}
