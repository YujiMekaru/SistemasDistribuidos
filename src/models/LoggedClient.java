/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.net.Socket;

/**
 *
 * @author madeinweb
 */
public class LoggedClient {

    private Socket userSocket;
    private User user;

    /**
     * @return the userSocket
     */
    public Socket getUserSocket() {
        return userSocket;
    }

    /**
     * @param userSocket the userSocket to set
     */
    public void setUserSocket(Socket userSocket) {
        this.userSocket = userSocket;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }
}
