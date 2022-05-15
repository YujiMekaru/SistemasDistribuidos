/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import infrastructure.repositories.IUserRepository;
import infrastructure.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import models.User;
import server.TcpServer2;

/**
 *
 * @author madeinweb
 */
public class UserService {
    
    private IUserRepository userRepository;
    List<User> onlineUsers;
    
    public UserService(ArrayList<User> onlineUsers)
    {
        this.onlineUsers = onlineUsers;
        userRepository = new UserRepository();
    }
    
    public boolean register(String username, String password)
    {
        try
        {      
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password);
            return userRepository.insert(newUser);
        }
        catch(Exception e)
        {
            System.out.println(e);
            return false;
        }
    }
    
    public boolean login(String username, String password)
    {
        try
        {
            User user = userRepository.login(username, password);
            
            if (user == null)
                return false;
                        
            onlineUsers.add(user);
            onlineUsers.forEach(a -> System.out.println("Logados : " + a.getUsername()));
            return true;
        }
        catch(Exception e)
        {
            System.out.println("aqui - " + e);
            return false;
        }
        
    }
    
    public void logout(String username)
    {
        try
        {
            User user = userRepository.logout(username);
            System.out.println("usuario encontrado logout : "+ user.getUsername());
            onlineUsers.forEach(u -> {
                if (u.getUsername().equals(user.getUsername()))
                {
                    onlineUsers.remove(u);
                }
            });
            System.out.println(username + " deslogado!");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

    }
}
