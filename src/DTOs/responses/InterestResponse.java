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
public class InterestResponse {
    private String buyerUsername;
    private String objectName;
    private int productID;

    /**
     * @return the buyerUsername
     */
    public String getBuyerUsername() {
        return buyerUsername;
    }

    /**
     * @param buyerUsername the buyerUsername to set
     */
    public void setBuyerUsername(String buyerUsername) {
        this.buyerUsername = buyerUsername;
    }

    /**
     * @return the objectName
     */
    public String getObjectName() {
        return objectName;
    }

    /**
     * @param objectName the objectName to set
     */
    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    /**
     * @return the productId
     */
    public int getProductID() {
        return productID;
    }

    /**
     * @param productID the productId to set
     */
    public void setProductID(int productID) {
        this.productID = productID;
    }
}
