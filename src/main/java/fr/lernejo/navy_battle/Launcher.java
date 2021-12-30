package fr.lernejo.navy_battle;

import java.io.IOException;

public class Launcher {
    public static void main(String[] args) throws IOException, InterruptedException {
        InstanceManager im = new InstanceManager();
        if (args.length == 1) {
            im.manageServer(args);
        }
        else if (args.length == 2) {
            im.manageClient(args);
        }
        else {
            System.out.println("Usage:\tLauncher\t<port>");
        }
    }
}
