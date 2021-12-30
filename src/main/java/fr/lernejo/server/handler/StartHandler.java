package fr.lernejo.server.handler;

import com.sun.net.httpserver.HttpExchange;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;


public class StartHandler implements HandlerInterface {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        String URI = exchange.getRequestURI().toString();
        int code = exchange.getResponseCode();
        System.out.println("["+requestMethod+"]\t"+URI+" ("+code+")");
        switch (requestMethod) {
            case "POST":
                post(exchange);
                break;
            default:
                verbError(exchange);
                break;
        }
    }

    @Override
    public void get(HttpExchange exchange) throws IOException {
    }

    @Override
    public void post(HttpExchange exchange) throws IOException {
        InputStream request = exchange.getRequestBody();
        JSONObject jso_request = new JSONObject(new JSONTokener(request));
        String result = jso_request.toString();
        System.out.println(result);
        // check the json schema
        // checkJsonSchema(exchange, result);
        // create the response
        postResponse(exchange, result);
    }

    @Override
    public void verbError(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(404, 0);
        exchange.getResponseBody().close();
    }

    public void postResponse(HttpExchange exchange, String result) throws IOException {
        String url = "http://"+exchange.getRequestHeaders().getFirst("Host");
        String uniqueID = UUID.randomUUID().toString();
        String message = "May the best code win";
        JSONObject jso = new JSONObject(result);
        jso.put("id", uniqueID);
        jso.put("url", url);
        jso.put("message", message);
        exchange.sendResponseHeaders(202, jso.toString().length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(jso.toString().getBytes());
        }
    }

    public void checkJsonSchema(HttpExchange exchange, String result) throws IOException {
        try (InputStream inputStream = getClass().getResourceAsStream("./schemas/message.json")) {
            JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
            Schema schema = SchemaLoader.load(rawSchema);
            schema.validate(new JSONObject(result));
        }
        catch (JSONException | IOException jse) {
            this.verbError(exchange);
        }
    }
}
