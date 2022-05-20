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

/**
 *
 * @author madeinweb
 */
public class NoDbUserService {
    List<User> onlineUsers;
    List<User> userList;
    
    public NoDbUserService()
    {
        onlineUsers = new ArrayList<>();
        userList = new ArrayList<>();
        
        populateScript();
    }
    
    private void populateScript()
    {
        register("abc","123");
        register("def","123");
        register("ghi","123");
    }
    
    public boolean register(String username, String password)
    {
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);

        for (int i = 0; i < userList.size(); i++)
        {
            if (userList.get(i).getUsername().equals(username))
                return false;
        }
        userList.add(newUser);
        return true;
    }
    
    
    public boolean login(String username, String password)
    {
        for (int i = 0; i < userList.size(); i++)
        {  
            if (userList.get(i).getUsername().equals(username) && 
                userList.get(i).getPassword().equals(password))
            {

                for (int j = 0; j < onlineUsers.size(); j++)
                {
                    if (onlineUsers.get(j).getUsername().equals(username))
                    {
                        return false;
                    }
                }
                
                User newUser = new User();
                newUser.setUsername(username);
                newUser.setPassword(password);
                onlineUsers.add(newUser);
                onlineUsers.forEach(a -> System.out.println("Logados : " + a.getUsername()));
                return true;
            }
        }
        return false;  
    }
    
    public boolean logout(String username)
    {
        for (int i = 0; i < onlineUsers.size(); i++)
        {
            if (onlineUsers.get(i).getUsername().equals(username))
            {
                onlineUsers.remove(onlineUsers.get(i));
                return true;
            }
        }
        return false;  
    }
    
}
