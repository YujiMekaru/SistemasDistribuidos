/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import models.LoggedClient;
import models.User;

/**
 *
 * @author madeinweb
 */
public class CommunicationService {
    
    private List<LoggedClient> clients;
    
    public CommunicationService()
    {
       clients = new ArrayList<>();
    }
    
    public void addClient(Socket socket, User user)
    {
        LoggedClient toAdd = new LoggedClient();
        toAdd.setUserSocket(socket);
        toAdd.setUser(user);
        clients.add(toAdd);
    }
    
    public void removeClient(String username)
    {
        for (int i = 0; i < clients.size(); i++)
        {
            if (clients.get(i).getUser().getUsername().equals(username))
            {
                clients.remove(i);
                return;
            }
        }
    }
    
    public boolean sendMessage(String username, String message) 
    {
        try
        {
            for (int i = 0; i < clients.size(); i++)
            {
                if (clients.get(i).getUser().getUsername().equals(username))
                {
                    Socket socket = clients.get(i).getUserSocket();
                    OutputStream output = socket.getOutputStream();
                    PrintWriter writer = new PrintWriter(output, true);
                    writer.println(message);
                    return true;
                }
            }    
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        return false;
    }
    
}
