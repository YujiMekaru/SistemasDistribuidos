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
public class InitChatRequestDTO extends DefaultRequest {
    private String buyer_username;
    private String seller_username;
    private int productId;

    /**
     * @return the buyer_username
     */
    public String getBuyer_username() {
        return buyer_username;
    }

    /**
     * @param buyer_username the buyer_username to set
     */
    public void setBuyer_username(String buyer_username) {
        this.buyer_username = buyer_username;
    }

    /**
     * @return the seller_username
     */
    public String getSeller_username() {
        return seller_username;
    }

    /**
     * @param seller_username the seller_username to set
     */
    public void setSeller_username(String seller_username) {
        this.seller_username = seller_username;
    }

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
}
