/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTOs.requests;

/**
 *
 * @author Yuji
 */
public class CreateProductRequestDTO extends DefaultRequest {

    private String productName;
    private String description;
    private float productValue;

    /**
     * @return the productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName the productName to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the productValue
     */
    public float getProductValue() {
        return productValue;
    }

    /**
     * @param productValue the productValue to set
     */
    public void setProductValue(float productValue) {
        this.productValue = productValue;
    }
}
