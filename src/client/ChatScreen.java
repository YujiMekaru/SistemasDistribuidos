/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import DTOs.requests.ChatMessageRequestDTO;
import com.google.gson.Gson;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Yuji
 */
public class ChatScreen extends DefaultScreen {
    private JTextArea textArea;
    private JTextField textField;
    private JButton btnSend;
    private String firstMessage;
    private String remoteUsername;
    private String clientUsername;
    
    public ChatScreen(JFrame frame, Middleware middleware, Socket socket, String remoteUsername, String message, String clientUsername) {
        super(frame, middleware, socket);
        this.firstMessage = message;
        this.remoteUsername = remoteUsername;
        this.clientUsername = clientUsername;
    }
    
    public void build()
    {
        textArea = new JTextArea();
        textField = new JTextField();
        btnSend = new JButton(">");
        textArea.setEditable(false);
                
        textArea.setBounds(20,20,360,360);
        textField.setBounds(20, 400, 300, 20);
        btnSend.setBounds(330, 400, 50, 20);
        frame.add(textArea);
        frame.add(textField);
        frame.add(btnSend);
        frame.setTitle("Conversa com "+ remoteUsername);
        if (!firstMessage.equals(""))
        {
            textArea.setText(remoteUsername + " : " + firstMessage);
        }
        
        btnSend.addActionListener((ActionListener) new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                textArea.setText(textArea.getText() + "\n" + clientUsername + " : " + textField.getText());
                Gson gson = new Gson();
                ChatMessageRequestDTO messageRequest = new ChatMessageRequestDTO();
                messageRequest.setOp(1200);
                messageRequest.setMessage(textField.getText());
                messageRequest.setUsername(remoteUsername);
                System.out.println("\nEnviado para o server : " + gson.toJson(messageRequest));
                try {
                    middleware.println(gson.toJson(messageRequest));
                    String serverResponse = middleware.readLine();
                    System.out.println("Servidor respondeu : "+ serverResponse);
                } catch (IOException ex) {
                    Logger.getLogger(ChatScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        frame.setSize(420,470);
        frame.setLayout(null);
        frame.setVisible(true);
    }
    
    public void receiveMessage(String message)
    {
        textArea.setText(textArea.getText() + "\n" + remoteUsername + " : " + message);
    }
}
