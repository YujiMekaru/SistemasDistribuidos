/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import DTOs.requests.LoginRequestDTO;
import DTOs.requests.RegisterRequestDTO;
import DTOs.responses.DefaultResponse;
import com.google.gson.Gson;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author madeinweb
 */
public class LoginScreen extends DefaultScreen {
    
    public LoginScreen(JFrame frame, BufferedReader in, PrintWriter out, Socket socket)
    {
        super(frame, in, out, socket);
    }
    
    public void build() {
     JLabel login = new JLabel("Bem Vindo");
     JLabel username = new JLabel("Usuario:");
     JLabel password = new JLabel("Senha:");
     JTextField usernameBox = new JTextField();
     JPasswordField passwordBox = new JPasswordField();
     JButton botaoCadastro = new JButton("Registro");
     JButton botaoLogin = new JButton("Login");

     login.setBounds(110, 1, 75, 75);
     username.setBounds(60, 75, 75, 25);
     usernameBox.setBounds(110, 75, 75, 25);
     password.setBounds(68, 115, 75, 25);
     passwordBox.setBounds(110, 115, 75, 25);
     botaoLogin.setBounds(100, 290, 100, 40);
     botaoCadastro.setBounds(100, 340, 100, 40);

     frame.setTitle("Sistema de Vendas");
     frame.add(botaoCadastro);
     frame.add(botaoLogin);
     frame.add(login);
     frame.add(username);
     frame.add(password);
     frame.add(usernameBox);
     frame.add(passwordBox);
     frame.setSize(300, 500);
     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     frame.setLayout(null);
     frame.setVisible(true);

     botaoLogin.addActionListener((ActionListener) new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             try {
                 String username = usernameBox.getText();
                 String password = String.valueOf(passwordBox.getPassword());
                 Gson gson = new Gson();
                 LoginRequestDTO loginRequest = new LoginRequestDTO();
                 loginRequest.setOp(100);
                 loginRequest.setUsername(username);
                 loginRequest.setPassword(password);

                 System.out.printf("\n\nMensagem Enviada para o Server : " + gson.toJson(loginRequest) + "\n\n");
                 out.println(gson.toJson(loginRequest));
                 String resposta = in.readLine();
                 System.out.println("Servidor respondeu : " + resposta);
                 DefaultResponse serverResponse = gson.fromJson(resposta, DefaultResponse.class);

                 if (serverResponse.getStatus() == 101)
                 {
                     JFrame afterLoginFrame = new JFrame();
                     AfterLoginScreen afterLoginScreen = new AfterLoginScreen(afterLoginFrame, in, out, socket, username);
                     showMessageDialog(null,"logado asdfasdf");
                     afterLoginScreen.build();
                 }
                 else
                 {
                     showMessageDialog(null, "Erro : " + resposta);
                 }

             } catch (IOException ex) {
                 Logger.getLogger(ClientInterface.class.getName()).log(Level.SEVERE, null, ex);
             }
         }
     });

     botaoCadastro.addActionListener((ActionListener) new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             try {
                 String username = usernameBox.getText();
                 String password = String.valueOf(passwordBox.getPassword());
                 Gson gson = new Gson();
                 RegisterRequestDTO registerRequest = new RegisterRequestDTO();
                 registerRequest.setOp(300);
                 registerRequest.setUsername(username);
                 registerRequest.setPassword(password);

                 System.out.printf("\n\nMensagem Enviada para o Server : " + gson.toJson(registerRequest) + "\n\n");
                 out.println(gson.toJson(registerRequest));
                 String resposta = in.readLine();
                 System.out.println("Servidor respondeu : " + resposta);

             } catch (IOException ex) {
                 Logger.getLogger(ClientInterface.class.getName()).log(Level.SEVERE, null, ex);
             }
         }
     });
 }
}
