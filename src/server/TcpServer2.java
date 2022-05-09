/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import infrastructure.repositories.UserRepository;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.List;
import models.User;

/**
 *
 * @author madeinweb
 */
public class TcpServer2 {
    
    public static void main(String[] args)
    {

      
       int port = 10009;
       
       try (ServerSocket serverSocket = new ServerSocket(port))
       {
           System.out.println("Server is listening on port : " + port);
           
           while (true)
           {
               Socket socket = serverSocket.accept();
               System.out.println("New client connected");
               
               new ServerThread(socket).start();
           }
       }
       catch (IOException ex)
       {
           System.out.println("Server exception : "+ ex.getMessage());
           ex.printStackTrace();
       }
    }
}
