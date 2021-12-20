package fr.lernejo.navy_battle;

import fr.lernejo.client.HttpClientDaemon;
import fr.lernejo.server.HttpDaemon;

import java.io.IOException;

public class Launcher {
    public static void main(String[] args) throws IOException, InterruptedException {
        int port;
        // for client
        String server_address;
        int server_port;

        if (args.length == 1) {
            try {
                // set port number
                port = Integer.parseInt(args[0]);
            }
            catch (NumberFormatException e) {
                System.out.println("Error: argument 2 is not a integer");
                return;
            }
            // starting server
            System.out.println("Starting server on port " + port + "...");
            HttpDaemon server = new HttpDaemon(port);
            server.start();
        }
        else if (args.length == 2) {
            try {
                // set port number
                port = Integer.parseInt(args[0]);
            }
            catch (NumberFormatException e) {
                System.out.println("Error: argument 1 is not a integer");
                return;
            }
            // set url
            String[] url_port = args[1].split("(://|:)");
            String _url, _port;
            if (url_port.length == 3) {
                _url = url_port[1];
                _port = url_port[2];
            } else {
                _url = url_port[0];
                _port = url_port[1];
            }
            System.out.println("Starting client on " + _url + ":" + _port + "...");

            server_address = _url;
            try {
                // set port number
                server_port = Integer.parseInt(_port);
            }
            catch (NumberFormatException e) {
                System.out.println("Error: argument 2 has no port");
                return;
            }

            // starting client
            HttpClientDaemon client = new HttpClientDaemon(port, server_address, server_port);
            client.postRequest();
        }
        else {
            System.out.println("Usage:\tLauncher\t<port>");
            return;
        }
    }
}
