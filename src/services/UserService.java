/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import models.User;

/**
 *
 * @author madeinweb
 */
public class UserService {
    private List<User> onlineUsers;
    private List<User> userList;
    private JList listAllUsers;
    private JList listOnlineUsers;
    
    public UserService(JList listAllUsers, JList listOnlineUsers)
    {
        onlineUsers = new ArrayList<>();
        userList = new ArrayList<>();
        this.listAllUsers = listAllUsers;
        this.listOnlineUsers = listOnlineUsers;
        
        populateScript();
    }
    
    private void populateScript()
    {
        register("Joao","123");
        register("Pedro","123");
        register("Andre","123");
    }
    

    
    public boolean register(String username, String password)
    {
        if (username.equals("") || password.equals(""))
            return false;
            
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        
        int lastId = 0;
        if (!userList.isEmpty())
            lastId = userList.get(userList.size()-1).getId();
        
        newUser.setId(lastId+1);

        for (int i = 0; i < userList.size(); i++)
        {
            if (userList.get(i).getUsername().equals(username))
                return false;
        }
        userList.add(newUser);
        
        System.out.println("usuario criado id : "+newUser.getId());

        DefaultListModel listModel = new DefaultListModel();
        userList.forEach(user -> listModel.addElement(user.getUsername()));
        listAllUsers.setModel(listModel);
        return true;
    }
    
    
    public User login(String username, String password)
    {
        for (int i = 0; i < userList.size(); i++)
        {  
            if (userList.get(i).getUsername().equals(username) && 
                userList.get(i).getPassword().equals(password))
            {
                // verifica se o usuario já esta logado, caso estiver retorna nulo
                for (int j = 0; j < onlineUsers.size(); j++)
                {
                    if (onlineUsers.get(j).getUsername().equals(username))
                    {
                        return null;
                    }
                }
                
//                User newUser = new User();
//                newUser.setUsername(username);
//                newUser.setPassword(password);
                User newUser = userList.get(i);
                
                onlineUsers.add(newUser);
                onlineUsers.forEach(a -> System.out.println("Logados : " + a.getUsername()));
                
                DefaultListModel listModel = new DefaultListModel();
                onlineUsers.forEach(user -> listModel.addElement(user.getUsername()));
                listOnlineUsers.setModel(listModel);
                return newUser;
            }
        }
        return null;  
    }
    
    public boolean logout(String username)
    {
        for (int i = 0; i < onlineUsers.size(); i++)
        {
            if (onlineUsers.get(i).getUsername().equals(username))
            {
                onlineUsers.remove(onlineUsers.get(i));
                
                DefaultListModel listModel = new DefaultListModel();
                onlineUsers.forEach(user -> listModel.addElement(user.getUsername()));
                listOnlineUsers.setModel(listModel);
                
                return true;
            }
        }
        return false;  
    }
    
}
