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
    private int status;
    
    public int getStatus()
    {
        return status;
    }
    
    public void setStatusCode(int value)
    {
        status = value;
    }
    
    public DefaultResponse(int status)
    {
        this.status = status;
    }
}
