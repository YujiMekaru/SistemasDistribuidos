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
public class ErrorResponse extends DefaultResponse 
{
    private String error;
    
    public ErrorResponse(int statusCode, String error)
    {
        super(statusCode);
        this.error = error;
    }
}
