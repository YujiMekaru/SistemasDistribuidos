/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

/**
 *
 * @author Yuji
 */
public class ChatUser {
    private String username;
    private ChatScreen chatScreen;

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the chatScreen
     */
    public ChatScreen getChatScreen() {
        return chatScreen;
    }

    /**
     * @param chatScreen the chatScreen to set
     */
    public void setChatScreen(ChatScreen chatScreen) {
        this.chatScreen = chatScreen;
    }
}
