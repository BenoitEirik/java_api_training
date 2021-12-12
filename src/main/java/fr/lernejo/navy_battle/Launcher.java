package fr.lernejo.navy_battle;

import fr.lernejo.server.HttpDaemon;

import java.io.IOException;

public class Launcher {
    public static void main(String[] args) throws IOException {
        int port;

        if (args.length == 1) {
            try {
                // set port number
                port = Integer.parseInt(args[0]);
            }
            catch (NumberFormatException e) {
                System.out.println("Error: argument 2 is not a integer");
                return;
            }
        }
        else {
            System.out.println("Usage:\tLauncher\t\t<port>");
            return;
        }

        // starting server
        System.out.println("Starting server on port " + port + "...");
        HttpDaemon server = new HttpDaemon(port);
    }
}
