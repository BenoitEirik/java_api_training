package fr.lernejo.server.handler;

import com.sun.net.httpserver.HttpExchange;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class FireHandler implements HandlerInterface {
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
    public void post(HttpExchange exchange) throws IOException {
    }

    @Override
    public void get(HttpExchange exchange) throws IOException {
        // get cell parameter
        String parameters = exchange.getRequestURI().getQuery();
        if (parameters == null) {
            verbError(exchange);
        }

        getResponse(exchange);
    }

    @Override
    public void verbError(HttpExchange exchange) throws IOException {
        System.out.println("Error 404");
        exchange.sendResponseHeaders(404, 0);
        exchange.getResponseBody().close();
    }

    public void getResponse(HttpExchange exchange) throws IOException {
        // String cell = parameters.split("=")[1];     // TODO: do something
        String consequence = "sunk";    // TODO: dynamically change sunk
        boolean shipLeft = true;        // TODO: dynamically change ship left
        JSONObject jso = new JSONObject();
        jso.put("consequence", consequence);
        jso.put("shipLeft", shipLeft);
        // checkJsonSchema(exchange, jso);
        exchange.sendResponseHeaders(200, jso.toString().length());
        System.out.println("test:" + jso.toString());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(jso.toString().getBytes());
        }
    }

    public void checkJsonSchema(HttpExchange exchange, JSONObject jso) throws IOException {
        try {
            InputStream inputStream = getClass().getResourceAsStream("./schemas/cell.json");
            JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
            Schema schema = SchemaLoader.load(rawSchema);
            schema.validate(jso);
        }
        catch (ValidationException jse) {
            this.verbError(exchange);
        }
    }
}
