/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTOs.responses;

import java.util.List;

/**
 *
 * @author Yuji
 */
public class WishlistResponse extends DefaultResponse {
    private List<InterestResponse> buyerArray;

    /**
     * @return the buyerArray
     */
    public List<InterestResponse> getBuyerArray() {
        return buyerArray;
    }

    /**
     * @param buyerArray the buyerArray to set
     */
    public void setBuyerArray(List<InterestResponse> buyerArray) {
        this.buyerArray = buyerArray;
    }
}
