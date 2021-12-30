package fr.lernejo.navy_battle;

import fr.lernejo.client.HttpClientDaemon;
import fr.lernejo.server.HttpDaemon;

import java.io.IOException;

import static java.lang.System.exit;

public class InstanceManager {
    public void manageServer(String[] args) throws IOException {
        int port;
        try {
            port = Integer.parseInt(args[0]);
        }
        catch (NumberFormatException e) {
            System.out.println("Error: argument 2 is not a integer");
            return;
        }
        System.out.println("Starting server on port " + port + "...");
        HttpDaemon server = new HttpDaemon(port);
        server.start();
    }

    public void manageClient(String[] args) throws IOException, InterruptedException {
        int port, server_port;
        try {
            port = Integer.parseInt(args[0]);
        }
        catch (NumberFormatException e) {
            System.out.println("Error: argument 1 is not a integer");
            return;
        }
        String[] url = setUrl(args[1]);
        int int_port = convertServerPort(url[1]);
        HttpClientDaemon client = new HttpClientDaemon(port, url[0], int_port);
        client.postRequest();
    }

    public String[] setUrl(String arg) {
        String[] url_port = arg.split("(://|:)");
        String _url, _port;
        if (url_port.length == 3) {
            _url = url_port[1];
            _port = url_port[2];
        } else {
            _url = url_port[0];
            _port = url_port[1];
        }
        System.out.println("Starting client on " + _url + ":" + _port + "...");
        return new String[]{_url, _port};
    }

    public int convertServerPort(String str_port) {
        int server_port = 0;
        try {
            // set port number
            server_port = Integer.parseInt(str_port);
        }
        catch (NumberFormatException e) {
            System.out.println("Error: argument 2 has no port");
            exit(-1);
        }
        return server_port;
    }
}
