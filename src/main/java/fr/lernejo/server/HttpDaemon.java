package fr.lernejo.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class HttpDaemon {
    final HttpServer server;

    public HttpDaemon(int port) throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(port), 0);

        // contexts
        this.server.createContext("/ping", new CallHandler());

        this.server.setExecutor(Executors.newFixedThreadPool(1));
        this.server.start();
    }
}
