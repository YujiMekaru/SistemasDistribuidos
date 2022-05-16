/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import DTOs.requests.LoginRequestDTO;
import DTOs.requests.RegisterRequestDTO;
import com.google.gson.Gson;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextPane;

/**
 *
 * @author madeinweb
 */
public class ClientInterface {
   public static void main(String[] args) {
        String serverHostname = new String ("127.0.0.1");
        if (args.length > 0)
           serverHostname = args[0];
        System.out.println ("Attemping to connect to host " +
                serverHostname + " on port 10009.");

        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        JFrame f = new JFrame();
        
        try {
            socket = new Socket(serverHostname, 20009);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                                        socket.getInputStream()));
            System.out.println("Connected!");
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + serverHostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                               + "the connection to: " + serverHostname);
            System.exit(1);
        }

	BufferedReader stdIn = new BufferedReader(
                                   new InputStreamReader(System.in));
        
        LoginScreen loginScreen = new LoginScreen(f, in, out, socket);
        loginScreen.build();
   }
   
    
}
