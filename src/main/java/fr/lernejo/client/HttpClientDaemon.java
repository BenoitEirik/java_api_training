package fr.lernejo.client;

import java.io.IOException;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

public class HttpClientDaemon {
    int port;
    String server_address;
    int server_port;

    HttpClient client;

    public HttpClientDaemon(int port, String server_address, int server_port) {
        this.port = port;
        this.server_address = server_address;
        this.server_port = server_port;

        this.client = HttpClient.newHttpClient();
    }

    public void postRequest() throws IOException, InterruptedException {
        System.out.print("[POST request] " + server_address + server_port + "/api/game/start");
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://" + server_address + ":" + server_port + "/api/game/start"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(BodyPublishers.ofString("{\"id\":\"1\", \"url\":\"http://localhost:" + server_port + "\", \"message\":\"Hello from client\"}"))
            .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        System.out.println(" (" + response.statusCode() + ")");
        System.out.println(response.body());
    }
}
