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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JFrame;
import models.User;
import services.NoDbUserService;

/**
 *
 * @author madeinweb
 */
public class TcpServer2 {
    
    public static void main(String[] args)
    {
       NoDbUserService userService = new NoDbUserService();
       JFrame frame = new JFrame();
       ServerScreen serverScreen = new ServerScreen(frame, userService);
       serverScreen.build();
       serverScreen.waitConnection();

    }

}
