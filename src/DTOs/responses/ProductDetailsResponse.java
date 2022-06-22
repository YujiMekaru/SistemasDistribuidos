/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTOs.responses;

/**
 *
 * @author Yuji
 */
public class ProductDetailsResponse extends DefaultResponse {
    private int productId;
    private float productValue;
    private String productName;
    private String description;
    private String sellerName;
    private boolean sellerStatus;

    /**
     * @return the productId
     */
    public int getProductId() {
        return productId;
    }

    /**
     * @param productId the productId to set
     */
    public void setProductId(int productId) {
        this.productId = productId;
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
     * @return the sellerName
     */
    public String getSellerName() {
        return sellerName;
    }

    /**
     * @param sellerName the sellerName to set
     */
    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    /**
     * @return the sellerStatus
     */
    public boolean isSellerStatus() {
        return sellerStatus;
    }

    /**
     * @param sellerStatus the sellerStatus to set
     */
    public void setSellerStatus(boolean sellerStatus) {
        this.sellerStatus = sellerStatus;
    }
}
