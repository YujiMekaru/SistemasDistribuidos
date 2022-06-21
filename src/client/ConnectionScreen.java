/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import DTOs.requests.LoginRequestDTO;
import DTOs.responses.DefaultResponse;
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
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author madeinweb
 */
public class ConnectionScreen {
    private JFrame frame;
    
    public ConnectionScreen(JFrame frame)
    {
        this.frame = frame;
    }
    
    public void build()
    {
        JLabel title = new JLabel("Bem Vindo");
        JLabel labelIp = new JLabel("IP do Servidor:");
        JLabel labelPort = new JLabel("Porta do Servidor:");
        JTextField ipBox = new JTextField();
        JTextField portBox = new JTextField();
        JButton botaoEnviar = new JButton("Enviar");

        title.setBounds(110, 1, 75, 75);
        labelIp.setBounds(50, 75, 100, 25);
        ipBox.setBounds(160, 75, 75, 25);
        labelPort.setBounds(50, 115, 110, 25);
        portBox.setBounds(160, 115, 75, 25);
        botaoEnviar.setBounds(50, 170, 185, 30);

        frame.setTitle("Sistema de Vendas");
        frame.add(botaoEnviar);
        frame.add(title);
        frame.add(labelIp);
        frame.add(labelPort);
        frame.add(ipBox);
        frame.add(portBox);
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setVisible(true);
     
        botaoEnviar.addActionListener((ActionListener) new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                    try 
                    {
                        String serverHostname;
                        int port;
                        if (ipBox.getText().equals(""))
                        {
                            serverHostname = new String ("127.0.0.1");
                            port = 20000;
                        }
                        else
                        {
                            serverHostname = ipBox.getText();
                            port = Integer.parseInt(portBox.getText());
                        }
                        //String serverHostname = new String ("127.0.0.1");
                        System.out.println ("Attemping to connect to host " +
                                serverHostname + " on port "+ port+".");

                        Socket socket = null;                   
                        PrintWriter out = null;                       
                        BufferedReader in = null;
                        JFrame f = new JFrame();

                        try 
                        {
                            socket = new Socket(serverHostname, port);
                            out = new PrintWriter(socket.getOutputStream(), true);
                            in = new BufferedReader(new InputStreamReader(
                                                        socket.getInputStream()));
                            System.out.println("Connected!");
                        } 
                        catch (UnknownHostException ex) 
                        {
                            System.err.println("Don't know about host: " + serverHostname);
                            System.exit(1);
                        } 
                        catch (IOException ex) 
                        {
                            showMessageDialog(null, "Não foi possível conectar no endereço digitado.");
                            System.err.println("Couldn't get I/O for "
                                               + "the connection to: " + serverHostname);
                            return;
                        }

                         BufferedReader stdIn = new BufferedReader(
                                                   new InputStreamReader(System.in));

                        LoginScreen loginScreen = new LoginScreen(f, in, out, socket);
                        loginScreen.build();
                    } 
                    catch (Exception ex) 
                    {
                          Logger.getLogger(ClientInterface.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
        });
    }
}
