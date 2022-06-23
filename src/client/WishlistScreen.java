/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import DTOs.requests.ListProductsRequestDTO;
import DTOs.requests.WishlistRequestDTO;
import DTOs.responses.InterestResponse;
import DTOs.responses.WishlistResponse;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import models.Interest;

/**
 *
 * @author Yuji
 */
public class WishlistScreen extends DefaultScreen {
    
    private JList componentWishlist;
    private List<InterestResponse> wishlist;
    private String username;
    
    public WishlistScreen(JFrame frame, Middleware middleware, Socket socket, String username) {
        super(frame, middleware, socket);
        wishlist = new ArrayList<>();
        this.username = username;
    }
    
    public void build()
    {
        componentWishlist = new JList();
        
        componentWishlist.setBounds(20, 20, 360, 360);
        
        frame.add(componentWishlist);
        frame.setSize(420,440);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setTitle("Lista de Interessados");
        
        try 
        {
            Gson gson = new Gson();
            WishlistRequestDTO request = new WishlistRequestDTO();
            request.setOp(700);
            request.setUsername(username);
            System.out.println("Enviou para o servidor : " + gson.toJson(request));
            middleware.println(gson.toJson(request));
            String resposta = middleware.readLine();
            System.out.println("Servidor respondeu : " + resposta);
            WishlistResponse response = gson.fromJson(resposta, WishlistResponse.class);
            wishlist = response.getBuyerArray();
            
            DefaultListModel listModel = new DefaultListModel();
            wishlist.forEach(interest -> listModel.addElement(interest.getBuyerUsername() + " tem interesse em " + interest.getObjectName()));
            componentWishlist.setModel(listModel);
               
        } catch (IOException ex) {
            Logger.getLogger(WishlistScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
