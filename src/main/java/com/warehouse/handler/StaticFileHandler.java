package com.warehouse.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.webjars.WebJarAssetLocator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

public class StaticFileHandler implements HttpHandler {
    private static final String BASE_DIRECTORY = "src/main/resources";
    private static final WebJarAssetLocator webJarAssetLocator = new WebJarAssetLocator();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        if (path.startsWith("/static/")) {
            serveStaticFile(exchange, path);
        } else if (path.startsWith("/webjars/")) {
            serveWebJarFile(exchange, path);
        } else {
            sendNotFoundResponse(exchange);
        }
    }

    private void serveStaticFile(HttpExchange exchange, String path) throws IOException {
        File file = getFile(path);
        if (file == null || !file.exists()) {
            sendNotFoundResponse(exchange);
        } else {
            exchange.sendResponseHeaders(200, file.length());
            OutputStream output = exchange.getResponseBody();
            Files.copy(file.toPath(), output);
            output.flush();
            output.close();
        }
    }

    private void serveWebJarFile(HttpExchange exchange, String path) throws IOException {
        try {
            String webJarPath = webJarAssetLocator.getFullPath(path.replaceFirst("/webjars/", ""));
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(webJarPath);
            if (inputStream == null) {
                sendNotFoundResponse(exchange);
                return;
            }
            exchange.sendResponseHeaders(200, 0);
            OutputStream output = exchange.getResponseBody();
            byte[] buffer = new byte[0x10000];
            int count;
            while ((count = inputStream.read(buffer)) >= 0) {
                output.write(buffer, 0, count);
            }
            output.flush();
            output.close();
            inputStream.close();
        } catch (IllegalArgumentException e) {
            sendNotFoundResponse(exchange);
        }
    }

    private void sendNotFoundResponse(HttpExchange exchange) throws IOException {
        String response = "Error 404 File not found.";
        exchange.sendResponseHeaders(404, response.length());
        OutputStream output = exchange.getResponseBody();
        output.write(response.getBytes());
        output.flush();
        output.close();
    }

    private File getFile(String path) {
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        return new File(BASE_DIRECTORY, path);
    }
}
