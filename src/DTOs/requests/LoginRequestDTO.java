/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTOs.requests;

/**
 *
 * @author madeinweb
 */
public class LoginRequestDTO extends DefaultRequest{
    private String username;
    private String password;
    
    public String getUsername()
    {
        return username;
    }
    
    public void setUsername(String value)
    {
        username = value;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String value)
    {
        password = value;
    }
}
