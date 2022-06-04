/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTOs.responses;

import java.util.List;
import models.Product;

/**
 *
 * @author Yuji
 */
public class ListProductsResponse extends DefaultResponse{
    private List<Product> productArray;

    public List<Product> getProductArray() {
        return productArray;
    }

    public void setProductArray(List<Product> productArray) {
        this.productArray = productArray;
    }
    
    public ListProductsResponse(int status, List<Product> productArray)
    {
        this.productArray = productArray;
        super.setStatusCode(status);
    }
}
