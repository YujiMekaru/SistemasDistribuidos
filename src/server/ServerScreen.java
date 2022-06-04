package server;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import services.ProductService;
import services.UserService;

public class ServerScreen {
    protected JFrame frame;
    protected BufferedReader in;
    protected PrintWriter out;
    protected Socket socket;
    protected UserService userService;
    protected ProductService productService;
    JButton buttonPort;
    JList listAllUsers;
    JList listOnlineUsers;
    
    public ServerScreen(JFrame frame) {
        this.frame = frame;
        build();
        this.userService = new UserService(listAllUsers, listOnlineUsers);
        this.productService = new ProductService();
    }
    
    public void waitConnection(int port)
    {
       buttonPort.setEnabled(false);
       Thread waitConnectionThread = new Thread(){
            public void run(){
                try (ServerSocket serverSocket = new ServerSocket(port))
                {
                    System.out.println("Server is listening on port : " + port);

                    while (true)
                    {
                        Socket socket = serverSocket.accept();
                        System.out.println("New client connected");

                        new ServerThread(socket, productService, userService, listAllUsers, listOnlineUsers).start();
                    }
                }
                catch (IOException ex)
                {
                    System.out.println("Server exception : "+ ex.getMessage());
                    ex.printStackTrace();
                }
            }
        };
       waitConnectionThread.start();
    }

    
    public void build(){
        JLabel title = new JLabel("Bem Vindo");
        JLabel portText = new JLabel("Informe a porta:");
        JTextField portBox = new JTextField();
        buttonPort = new JButton("Salvar");
        listAllUsers = new JList();
        listOnlineUsers = new JList();
        
        JLabel labelOnlineUsers = new JLabel("Online:");
        JLabel labelAllUsers = new JLabel("Cadastrados:");
        
        title.setBounds(200, 1, 75, 75);
        
        listAllUsers.setBounds(200, 75, 100, 200);
        listOnlineUsers.setBounds(325, 75, 100, 200);
        
        portText.setBounds(60, 75, 100,25);
        portBox.setBounds(60, 100, 80, 25);
        buttonPort.setBounds(60, 140, 80, 40);

        labelOnlineUsers.setBounds(325, 55, 100, 25);
        labelAllUsers.setBounds(200, 55, 100, 25);
        
        frame.setTitle("Sistema de Vendas");
        frame.add(title);
        frame.add(buttonPort);
        frame.add(portText);
        frame.add(portBox);
        frame.add(listAllUsers);
        frame.add(listOnlineUsers);
        frame.add(labelOnlineUsers);
        frame.add(labelAllUsers);
        frame.setSize(500, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setVisible(true);

        buttonPort.addActionListener((ActionListener) new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int port = Integer.parseInt(portBox.getText());
                    waitConnection(port);
            }
        });

    }

}
