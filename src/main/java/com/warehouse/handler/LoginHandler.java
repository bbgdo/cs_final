package com.warehouse.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.warehouse.config.ThymeleafConfig;
import com.warehouse.util.JWTAuth;
import org.thymeleaf.context.Context;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


public class LoginHandler implements HttpHandler {
    private static final String LOGIN = "admin";
    private static final String PASSWORD_MD5 = "21232f297a57a5a743894a0e4a801fc3"; // "admin" in md5

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            handleLoginPage(exchange);
        }
        else if("POST".equalsIgnoreCase(exchange.getRequestMethod())){
            handleLogin(exchange);
        }
        else {
            exchange.sendResponseHeaders(405, -1);
            exchange.close();
        }
    }

    private void handleLoginPage(HttpExchange exchange) throws IOException {
        Context context = new Context();
        String response = ThymeleafConfig.getTemplateEngine().process("login", context);
        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }

    private void handleLogin(HttpExchange exchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        // make cleaner later
        Map<String, String> formData = parse(sb.toString());

        String login = formData.get("username");
        String password = formData.get("password");

        try {
            if (authenticate(login, password)) {
                String token = JWTAuth.createJWTToken(login);
                String responseText = "Ok " + token;
                byte[] response = responseText.getBytes();

                exchange.sendResponseHeaders(200, response.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response);
                }
            } else {
                String responseText = "Unauthorized";
                byte[] response = responseText.getBytes();

                exchange.sendResponseHeaders(401, response.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response);
                }
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean authenticate(String login, String password) throws NoSuchAlgorithmException {
        return LOGIN.equals(login) && md5(password).equals(PASSWORD_MD5);
    }

    private String md5(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(input.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }


    // make cleaner later
    public static Map<String, String> parse(String formData) throws UnsupportedEncodingException {
        Map<String, String> result = new HashMap<>();
        String[] pairs = formData.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length > 1) {
                String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8.name());
                String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8.name());
                result.put(key, value);
            }
        }
        return result;
    }
}
