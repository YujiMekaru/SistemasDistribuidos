/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTOs.responses;

/**
 *
 * @author madeinweb
 */
public class DefaultResponse {
    private int statusCode;
    
    public int getStatusCode()
    {
        return statusCode;
    }
    
    public void setStatusCode(int value)
    {
        statusCode = value;
    }
    
    public DefaultResponse(int statusCode)
    {
        this.statusCode = statusCode;
    }
}
