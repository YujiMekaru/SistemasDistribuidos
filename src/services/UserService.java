/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import infrastructure.repositories.IUserRepository;
import infrastructure.repositories.UserRepository;
import models.User;

/**
 *
 * @author madeinweb
 */
public class UserService {
    
    private IUserRepository userRepository;
    
    public UserService()
    {
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
            User user = userRepository.find(username, password);
            System.out.println("Usuario encontrado!");
            return true;
        }
        catch(Exception e)
        {
            System.out.println(e);
            return false;
        }
        
    }
    
    public void logout(String username)
    {
        System.out.println("Logout feito!");
    }
}
