package server;

import models.User;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import services.NoDbUserService;

public class ServerInterface {

    public static void main(String[] args){

        int port = 20009;
        JFrame f = new JFrame();

        PrintWriter out = null;
        BufferedReader in = null;
        Socket socket = null;

        // NoDbUserService userService = new NoDbUserService();
        // ServerScreen serverScreen = new ServerScreen(f, in, out, socket);
        // serverScreen.build();

        try (ServerSocket serverSocket = new ServerSocket(port))
        {
            System.out.println("Server is listening on port : " + port);

            while (true)
            {
                socket = serverSocket.accept();
                System.out.println("New client connected");

//                new ServerThread(socket, userService).start();
            }
        }
        catch (IOException ex)
        {
            System.out.println("Server exception : "+ ex.getMessage());
            ex.printStackTrace();
        }

    }
}
