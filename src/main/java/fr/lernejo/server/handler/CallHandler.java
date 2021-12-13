package fr.lernejo.server.handler;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class CallHandler implements HandlerInterface {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        String URI = exchange.getRequestURI().toString();
        int code = exchange.getResponseCode();

        System.out.println("["+requestMethod+"]\t"+URI+" ("+code+")");

        switch (requestMethod) {
            case "GET":
                get(exchange);
                break;
            default:
                verbError(exchange);
                break;
        }
    }

    @Override
    public void get(HttpExchange exchange) throws IOException {
        String response = "OK";
        exchange.sendResponseHeaders(200, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    @Override
    public void post(HttpExchange exchange) throws IOException {

    }

    @Override
    public void verbError(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(404, 0);
        exchange.getResponseBody().close();
    }
}
