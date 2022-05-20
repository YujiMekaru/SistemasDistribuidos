package server;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import services.NoDbUserService;

public class ServerScreen {
    protected JFrame frame;
    protected BufferedReader in;
    protected PrintWriter out;
    protected Socket socket;
    protected NoDbUserService userService;

    public ServerScreen(JFrame frame, NoDbUserService userService) {
        this.frame = frame;
        this.userService = userService;
    }
    
    public void waitConnection(int port)
    {
       
        try (ServerSocket serverSocket = new ServerSocket(port))
        {
            System.out.println("Server is listening on port : " + port);

            while (true)
            {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                new ServerThread(socket, userService).start();
            }
        }
        catch (IOException ex)
        {
            System.out.println("Server exception : "+ ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public void build(){
        JLabel server = new JLabel("Bem Vindo");
        JLabel portText = new JLabel("Informe a porta:");
        JTextField portBox = new JTextField();
        JButton buttonPort= new JButton("Salvar");

        server.setBounds(110, 1, 75, 75);
        portText.setBounds(105, 75, 100,25);
        portBox.setBounds(110   , 100, 80, 25);
        buttonPort.setBounds(100, 340, 100, 40);
        buttonPort.setBounds(100, 340, 100, 40);

        frame.setTitle("Sistema de Vendas");
        frame.add(server);
        frame.add(buttonPort);
        frame.add(portText);
        frame.add(portBox);
        frame.setSize(300, 500);
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
