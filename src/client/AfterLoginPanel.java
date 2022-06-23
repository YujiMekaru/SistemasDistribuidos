/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import DTOs.requests.*;
import DTOs.responses.*;
import com.google.gson.Gson;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import static javax.swing.JOptionPane.showMessageDialog;
import models.Product;
import server.ServerThread;

/**
 *
 * @author Yuji
 */
public class AfterLoginPanel extends javax.swing.JPanel {

    /**
     * Creates new form AfterLoginPanel
     */
    private JFrame frame;
    private Socket socket;
    private String username;
    private List<Product> allProducts;
    private boolean isHomePage;
    private Thread listenerThread;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnRemove;
    private JButton btnBuy;
    private Middleware middleware;
    
    public AfterLoginPanel(JFrame frame, Middleware middleware, Socket socket, String username) {
        this.middleware = middleware;
        middleware.setClientUsername(username);
        this.socket = socket;
        this.frame = frame;
        this.username = username;
        allProducts = new ArrayList<>();
        isHomePage = true;
        initComponents();
        btnAdd = new JButton();
        btnEdit = new JButton();
        btnRemove = new JButton();
        btnBuy = new JButton();
        
        btnAdd.setText("Adicionar");
        btnEdit.setText("Editar");
        btnRemove.setText("Remover");
        btnBuy.setText("Comprar");
        
        btnAdd.setFont(new Font("Arial", Font.BOLD, 9));
        btnRemove.setFont(new Font("Arial", Font.BOLD, 9));
        
        btnAdd.setBounds(160, 420, 80 ,30);
        btnEdit.setBounds(250, 420, 80, 30);
        btnRemove.setBounds(340, 420, 80 ,30);
        btnBuy.setBounds(160,420, 250, 30);

        frame.add(btnAdd);
        frame.add(btnEdit);
        frame.add(btnRemove);
        frame.add(btnBuy);
        
        frame.setTitle(username);
        btnAdd.setVisible(false);       
        btnEdit.setVisible(false);
        btnRemove.setVisible(false);
        createButtonActions();
        
        listCreateRequest(400);
        //initProductListener();
        
    }
    
    public void createButtonActions()
    {
        btnBuy.addActionListener((ActionListener) new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int index = listProducts.getSelectedIndex();
                Product product = allProducts.get(index);
                ProductDetailsRequestDTO interestRequest = new ProductDetailsRequestDTO();
                interestRequest.setOp(600);
                interestRequest.setProductId(product.getId());
                interestRequest.setUsername(username);
                
                try 
                {
                    Gson gson = new Gson();
                    System.out.printf("\nMensagem Enviada para o Server : " + gson.toJson(interestRequest) + "\n");
                    middleware.println(gson.toJson(interestRequest));
                    String resposta = middleware.readLine();
                    System.out.println("Servidor respondeu : " + resposta);
                    ProductDetailsResponse detailsResponse = gson.fromJson(resposta, ProductDetailsResponse.class);
                    if (detailsResponse.getSellerStatus())
                    {
                        InitChatRequestDTO initChatRequest = new InitChatRequestDTO();
                        initChatRequest.setOp(1100);
                        initChatRequest.setBuyer_username(username);
                        initChatRequest.setProductId(product.getId());
                        initChatRequest.setSeller_username(product.getUsername());

                        try
                        {
                            System.out.printf("\nMensagem Enviada para o Server : " + gson.toJson(initChatRequest) + "\n");
                            middleware.println(gson.toJson(initChatRequest));
                            String serverresposta = middleware.readLine();
                            System.out.println("Servidor respondeu : " + serverresposta);
                            DefaultResponse response = gson.fromJson(serverresposta, DefaultResponse.class);
                            if (response.getStatus() == 1101)
                            {
                                JFrame chatFrame = new JFrame();
                                ChatScreen chatScreen = new ChatScreen(chatFrame, middleware, socket, product.getUsername(), "", username);
                                ChatUser toAdd = new ChatUser();
                                toAdd.setChatScreen(chatScreen);
                                toAdd.setUsername(product.getUsername());
                                boolean result = middleware.addChatUser(toAdd);
                                if (result)
                                    chatScreen.build();
                            }
                         }
                        catch(IOException ex)
                        {
                            System.out.println(ex.getMessage());
                        }
                    }
                   
                }
                catch (IOException ex)
                {
                    System.out.println(ex.getMessage());
                }
                

                

            }
        });
        
        btnEdit.addActionListener((ActionListener) new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                JFrame formFrame = new JFrame();
                int index = listProducts.getSelectedIndex();
                Product product = allProducts.get(index);
                ProductFormScreen formScreen = new ProductFormScreen(formFrame,middleware,socket,product);
                formScreen.build();
            }
        });
        
        btnAdd.addActionListener((ActionListener) new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                JFrame formFrame = new JFrame();
                ProductFormScreen formScreen = new ProductFormScreen(formFrame, middleware,socket,null);
                formScreen.build();
            }
        });
        
        btnRemove.addActionListener((ActionListener) new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try
                {
                    Gson gson = new Gson();
                    DeleteProductRequestDTO deleteRequest = new DeleteProductRequestDTO();
                    deleteRequest.setOp(1000);
                    int index = 0;
                    if (listProducts.getSelectedIndex() == -1)
                         return;
                    index = listProducts.getSelectedIndex();
                    int productId = allProducts.get(index).getId();
                    //deleteRequest.setUsername(username);
                    deleteRequest.setProductId(productId);
                    System.out.printf("\n\nMensagem Enviada para o Server : " + gson.toJson(deleteRequest) + "\n\n");
                    middleware.println(gson.toJson(deleteRequest));
                    String resposta = middleware.readLine();
                    System.out.println("Servidor respondeu : " + resposta);
                    
                    allProducts.remove(index);
                    
                    DecimalFormat moneyFormat = new DecimalFormat("0.00");
                    DefaultListModel listModel = new DefaultListModel();
                    allProducts.forEach(product -> listModel.addElement(product.getName() + " - R$" + moneyFormat.format(product.getValue()) + " - " + product.getUsername()));
                    listProducts.setModel(listModel);
                }
                catch (IOException ex)
                {
                    System.out.println(ex.getMessage());
                }
            }
        });
    }

    
    public Thread instantiateListener()
    {
        Thread productListenerThread = new Thread(){
            public void run(){
                
                try {
                    
                    while (true)
                    {
                       Thread.sleep(10000);
                       
                       if (!isHomePage) // Produtos do Usuario
                            listCreateRequest(500);
                       else
                            listCreateRequest(400);
                    }
                } 
                catch (InterruptedException ex)
                {
                    System.out.println(ex.getMessage());
                }
            }
        };
        return productListenerThread;
    }
    
    public void initProductListener()
    {
        listenerThread = instantiateListener();
        listenerThread.start();
    }
    
    public void stopProductListener()
    {
        listenerThread.interrupt();
    }
    
    public void listCreateRequest(int op)
    {
        try 
        {
               Gson gson = new Gson();
               ListProductsRequestDTO listRequest = new ListProductsRequestDTO();
               listRequest.setOp(op);
               System.out.printf("\nMensagem Enviada para o Server : " + gson.toJson(listRequest) + "\n");
               middleware.println(gson.toJson(listRequest));
               String resposta = middleware.readLine();
               System.out.println("Servidor respondeu : " + resposta);
               ListProductsResponse serverResponse = gson.fromJson(resposta, ListProductsResponse.class);
               allProducts = serverResponse.getProductArray();
               DecimalFormat moneyFormat = new DecimalFormat("0.00");
               DefaultListModel listModel = new DefaultListModel();
               allProducts.forEach(product -> listModel.addElement(product.getName() + " - R$" + moneyFormat.format(product.getValue()) + " - " + product.getUsername()));
               listProducts.setModel(listModel);
        } 
        catch (IOException ex) {
            Logger.getLogger(ClientInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelMenu = new javax.swing.JPanel();
        buttonHome = new javax.swing.JButton();
        buttonProducts = new javax.swing.JButton();
        buttonLogout = new javax.swing.JButton();
        btnWishlist = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        listProducts = new javax.swing.JList<>();

        panelMenu.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        buttonHome.setText("Pagina Inicial");
        buttonHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonHomeActionPerformed(evt);
            }
        });

        buttonProducts.setText("Meus Produtos");
        buttonProducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonProductsActionPerformed(evt);
            }
        });

        buttonLogout.setText("Logout");
        buttonLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLogoutActionPerformed(evt);
            }
        });

        btnWishlist.setText("Interessados");
        btnWishlist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnWishlistActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelMenuLayout = new javax.swing.GroupLayout(panelMenu);
        panelMenu.setLayout(panelMenuLayout);
        panelMenuLayout.setHorizontalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonProducts, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                    .addComponent(buttonLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnWishlist, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelMenuLayout.setVerticalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonHome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonProducts)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnWishlist)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonLogout)
                .addContainerGap())
        );

        listProducts.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(listProducts);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void buttonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLogoutActionPerformed
         try {
                 Gson gson = new Gson();
                 LogoutRequestDTO logoutRequest = new LogoutRequestDTO();
                 logoutRequest.setOp(200);
                 logoutRequest.setUsername(username);
                 System.out.printf("\n\nMensagem Enviada para o Server : " + gson.toJson(logoutRequest) + "\n\n");
                 middleware.println(gson.toJson(logoutRequest));
                 String resposta = middleware.readLine();
                 System.out.println("Servidor respondeu : " + resposta);
                 DefaultResponse serverResponse = gson.fromJson(resposta, DefaultResponse.class);
                 if (serverResponse.getStatus() == 201)
                 {
                     frame.setVisible(false);
                     frame.dispose();
                 }
                 else
                 {
                     showMessageDialog(null, "Erro : " + resposta);
                 }

             } catch (IOException ex) {
                 Logger.getLogger(ClientInterface.class.getName()).log(Level.SEVERE, null, ex);
             }
    }//GEN-LAST:event_buttonLogoutActionPerformed

    private void buttonProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonProductsActionPerformed
        if (!isHomePage)
            return;
        btnAdd.setVisible(true);       
        btnEdit.setVisible(true);
        btnRemove.setVisible(true);
        btnBuy.setVisible(false);
        isHomePage = false;
        listCreateRequest(500);

    }//GEN-LAST:event_buttonProductsActionPerformed

    private void buttonHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonHomeActionPerformed
        if (isHomePage)
            return;
        
        isHomePage = true;
        btnAdd.setVisible(false);       
        btnEdit.setVisible(false);
        btnRemove.setVisible(false);
        btnBuy.setVisible(true);
        listCreateRequest(400);
       // initProductListener();
    }//GEN-LAST:event_buttonHomeActionPerformed

    private void btnWishlistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWishlistActionPerformed
        JFrame wishlistFrame = new JFrame();
        WishlistScreen wishlistScreen = new WishlistScreen(wishlistFrame, middleware,socket,username);
        wishlistScreen.build();
    }//GEN-LAST:event_btnWishlistActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnWishlist;
    private javax.swing.JButton buttonHome;
    private javax.swing.JButton buttonLogout;
    private javax.swing.JButton buttonProducts;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> listProducts;
    private javax.swing.JPanel panelMenu;
    // End of variables declaration//GEN-END:variables
}

