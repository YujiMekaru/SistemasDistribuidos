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
public class LoginResponse extends DefaultResponse {
    private int wishlist;

    /**
     * @return the wishlist
     */
    public int getWishlist() {
        return wishlist;
    }

    /**
     * @param wishlist the wishlist to set
     */
    public void setWishlist(int wishlist) {
        this.wishlist = wishlist;
    }
}
