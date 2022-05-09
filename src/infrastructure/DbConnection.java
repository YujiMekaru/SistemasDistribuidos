/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author madeinweb
 */
public class DbConnection {  
   
    // Conecta ao banco mySql e retorna a conexão
    public static Connection getMySqlConnection() throws Exception
    {
            Connection conn = null;
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn =
            DriverManager.getConnection("jdbc:mysql://localhost:3306/distribuidosapp","root","admin");

            return conn;
        
    }
    
    
}
