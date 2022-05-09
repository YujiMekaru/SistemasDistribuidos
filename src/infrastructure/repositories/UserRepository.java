/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infrastructure.repositories;

import infrastructure.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.User;

/**
 *
 * @author madeinweb
 */
public class UserRepository implements IUserRepository {

    @Override
    public User getById(int id) throws Exception {
        Connection conn = DbConnection.getMySqlConnection();
        String query = "SELECT * FROM user WHERE iduser = "+id;
        PreparedStatement statement = conn.prepareStatement(query);
        System.out.printf("*\n"+query+"\n*\n");
        ResultSet rs = statement.executeQuery();

        if (rs.next() == false)
            throw new Exception("Could not find User by Id");
        
        User user = new User(Integer.parseInt(rs.getString("iduser")), rs.getString("username"), rs.getString("password"));
        rs.close();
        statement.close();
        conn.close();
        return user;
    }

    @Override
    public List<User> getAll() throws Exception {
        Connection conn = DbConnection.getMySqlConnection();
        String query = "SELECT * FROM user";
        PreparedStatement statement = conn.prepareStatement(query);
        System.out.printf("*\n"+query+"\n*\n");
        ResultSet rs = statement.executeQuery();
        List<User> users = new ArrayList<User>();
        while (rs.next())
        {
            User user = new User(Integer.parseInt(rs.getString("iduser")), rs.getString("username"), rs.getString("password"));
            users.add(user);
        }
        rs.close();
        statement.close();
        conn.close();
        return users;
    }

    @Override
    public boolean insert(User user) throws Exception {
        Connection conn = DbConnection.getMySqlConnection();
        String query = "INSERT INTO user (username, password) values (?, ?)";
 
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        
        System.out.printf("*\n"+statement.toString()+"\n*\n");
                
        boolean result = statement.execute();
        statement.close();
        conn.close();
        return result;
    }

    @Override
    public boolean update(User user) throws Exception {
        Connection conn = DbConnection.getMySqlConnection();
        String query = "UPDATE user SET username = ?, password = ? WHERE iduser = ?";
        
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        statement.setInt(3, user.getId());
        
        System.out.printf("*\n"+statement.toString()+"\n*\n");
                
        boolean result = statement.execute();
        statement.close();
        conn.close();
        return result;
    }

    @Override
    public boolean delete(int id) throws Exception {
        Connection conn = DbConnection.getMySqlConnection();
        String query = "DELETE FROM user WHERE iduser = "+id;
        PreparedStatement statement = conn.prepareStatement(query);
        boolean result = statement.execute();
        statement.close();
        conn.close();
        return result;
    }

    @Override
    public User find(String username, String password) throws Exception {
        Connection conn = DbConnection.getMySqlConnection();
        String query = "SELECT * FROM user WHERE username = ? AND password = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, password);
        System.out.printf("*\n"+statement.toString()+"\n*\n");
        ResultSet rs = statement.executeQuery();

        if (rs.next() == false)
            return null;
        
        User user = new User(Integer.parseInt(rs.getString("iduser")), rs.getString("username"), rs.getString("password"));
        rs.close();
        statement.close();
        conn.close();
        return user;
    }


    
}
