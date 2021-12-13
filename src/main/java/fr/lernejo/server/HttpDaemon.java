package fr.lernejo.server;

import com.sun.net.httpserver.HttpServer;
import fr.lernejo.server.handler.StartHandler;
import fr.lernejo.server.handler.CallHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class HttpDaemon {
    final HttpServer server;

    public HttpDaemon(int port) throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(port), 0);

        // contexts
        this.server.createContext("/ping", new CallHandler());
        this.server.createContext("/api/game/start", new StartHandler());

        this.server.setExecutor(Executors.newFixedThreadPool(1));
    }

    public void start() {
        this.server.start();
    }
}
