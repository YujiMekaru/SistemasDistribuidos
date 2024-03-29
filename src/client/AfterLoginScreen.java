/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import DTOs.requests.CreateProductRequestDTO;
import DTOs.requests.DeleteProductRequestDTO;
import DTOs.requests.EditProductRequestDTO;
import DTOs.requests.ListProductsRequestDTO;
import DTOs.requests.LoginRequestDTO;
import DTOs.requests.LogoutRequestDTO;
import DTOs.responses.DefaultResponse;
import com.google.gson.Gson;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.*;
import static javax.swing.JOptionPane.showMessageDialog;
import models.User;

/**
 *
 * @author madeinweb
 */
public class AfterLoginScreen extends DefaultScreen {
    private String username;
    
    public AfterLoginScreen(JFrame frame, Middleware middleware, Socket socket, String username)
    {
        super(frame, middleware, socket);
        this.username = username;
    }
    
    public void build()
    {
        AfterLoginPanel panel = new AfterLoginPanel(frame, middleware, socket, username);
        frame.getContentPane().add(panel);
        frame.setSize(500,500);
        frame.setVisible(true);
    }
    
//    public void build()
//    {
//        JLabel login = new JLabel(username);
//        JButton botaoLogout = new JButton("Logout");
//
//
//        login.setBounds(80, 1, 75, 75);
//        botaoLogout.setBounds(40, 90, 100, 40);
//        
//        frame.add(login);
//        frame.add(botaoLogout);
//        
//        frame.setSize(200, 200);
//        frame.setLayout(null);
//        frame.setVisible(true);
//        
//        
//        botaoLogout.addActionListener((ActionListener) new ActionListener() {
//         @Override
//         public void actionPerformed(ActionEvent e) {
//             try {
//                 Gson gson = new Gson();
//                 LogoutRequestDTO logoutRequest = new LogoutRequestDTO();
//                 logoutRequest.setOp(200);
//                 logoutRequest.setUsername(username);
//                 System.out.printf("\n\nMensagem Enviada para o Server : " + gson.toJson(logoutRequest) + "\n\n");
//                 out.println(gson.toJson(logoutRequest));
//                 String resposta = in.readLine();
//                 System.out.println("Servidor respondeu : " + resposta);
//                 DefaultResponse serverResponse = gson.fromJson(resposta, DefaultResponse.class);
//                 if (serverResponse.getStatus() == 201)
//                 {
//                     close();
//                 }
//                 else
//                 {
//                     showMessageDialog(null, "Erro : " + resposta);
//                 }
//
//             } catch (IOException ex) {
//                 Logger.getLogger(ClientInterface.class.getName()).log(Level.SEVERE, null, ex);
//             }
////            try {
////                Gson gson = new Gson();
////                //ListProductsRequestDTO listAllRequest = new ListProductsRequestDTO();
////                //listAllRequest.setOp(800);
////                DeleteProductRequestDTO createProductRequest = new DeleteProductRequestDTO();
////                createProductRequest.setOp(1000);
////                createProductRequest.setProductId(2);
////
////                System.out.printf("\n\nMensagem Enviada para o Server : " + gson.toJson(createProductRequest) + "\n\n");
////                out.println(gson.toJson(createProductRequest));
////                String resposta = in.readLine();
////                System.out.println("Servidor respondeu : " + resposta);  
////            }
////            catch (IOException ex)
////            {
////                Logger.getLogger(ClientInterface.class.getName()).log(Level.SEVERE, null, ex);
////            }
//         }
//     });
//    }
    
    public void close()
    {
        frame.setVisible(false);
        frame.dispose();
    }
}
