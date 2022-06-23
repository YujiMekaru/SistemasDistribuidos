/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import DTOs.requests.ChatMessageRequestDTO;
import DTOs.requests.DefaultRequest;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;


/**
 *
 * @author Yuji
 */
public class Middleware {
    
    private String serverMessage;
    private String chatMessage;
    
    public BufferedReader in;
    public PrintWriter out;
    
    private String clientUsername;
    
    private List<ChatUser> usersWithChat;

    private Socket socket;
    

    
    public Middleware(Socket socket) throws IOException
    {
        usersWithChat = new ArrayList<>();
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        System.out.println(out);
        Middleware mid = this;
        Thread chatListenerThread = new Thread(){
            public void run(){
                System.out.println("rodou a thread");
                Gson gson = new Gson();
               while (true)
               {
                    try 
                    {
                        String json = in.readLine();
                        System.out.println("THREAD RECEBEU : " +json);
                        DefaultRequest request = gson.fromJson(json, DefaultRequest.class);
                        if (request.getOp() == 1300)
                        {
                            ChatMessageRequestDTO messageRequest = gson.fromJson(json, ChatMessageRequestDTO.class);
                            String emitterUser = messageRequest.getUsername();
                            boolean hasChat = false;
                            for (int i = 0; i < usersWithChat.size(); i++)
                            {
                                if (usersWithChat.get(i).getUsername().equals(emitterUser))
                                {
                                    hasChat = true;
                                    break;
                                }
                            }
                            if (!hasChat)
                            {
                                JFrame chatFrame = new JFrame();
                                ChatScreen chatScreen = new ChatScreen(chatFrame, mid, socket, messageRequest.getUsername(), messageRequest.getMessage(), clientUsername);
                                ChatUser toAdd = new ChatUser();
                                toAdd.setChatScreen(chatScreen);
                                toAdd.setUsername(messageRequest.getUsername());
                                usersWithChat.add(toAdd);
                                chatScreen.build();
                            }
                            else
                            {
                                for (int i = 0; i < usersWithChat.size(); i++)
                                {
                                    if (usersWithChat.get(i).getUsername().equals(emitterUser))
                                    {
                                        usersWithChat.get(i).getChatScreen().receiveMessage(messageRequest.getMessage());
                                    }
                                }
                            }
                        }
                        else
                        {
                            serverMessage = json;
                        }
                    }
                    catch(IOException ex)
                    {
                        System.out.println("Erro no middleware : " + ex.getMessage());
                    }
               }
            }
        };
        chatListenerThread.start();
    }
    
    public boolean addChatUser(ChatUser chatUser)
    {
        for (int i = 0; i < usersWithChat.size(); i++)
        {
            if (usersWithChat.get(i).getUsername().equals(chatUser.getUsername()))
            {
                return false;
            }
        }
                            
        usersWithChat.add(chatUser);
        return true;
    }
    
    public String readLine() throws IOException
    {
        try {
            Thread.sleep(250);
        } catch (InterruptedException ex) {
            Logger.getLogger(Middleware.class.getName()).log(Level.SEVERE, null, ex);
        }
        return serverMessage;
    }
    
    public void println(String message) throws IOException
    {
        out.println(message);
    }

    /**
     * @return the clientUsername
     */
    public String getClientUsername() {
        return clientUsername;
    }

    /**
     * @param clientUsername the clientUsername to set
     */
    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }
    
   
}
