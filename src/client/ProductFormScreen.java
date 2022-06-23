/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import DTOs.requests.CreateProductRequestDTO;
import DTOs.requests.DeleteProductRequestDTO;
import DTOs.requests.EditProductRequestDTO;
import com.google.gson.Gson;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.*;
import models.Product;

/**
 *
 * @author Yuji
 */
public class ProductFormScreen extends DefaultScreen{
    
    private JLabel lblName;
    private JLabel lblValue;
    private JLabel lblDescription;
    private JTextField txtName;
    private JTextField txtValue;
    private JTextField txtDescription;
    private JButton btnConfirm;
    
    private Product product;
    
    public ProductFormScreen(JFrame frame, Middleware middleware, Socket socket, Product product) {
        super(frame, middleware, socket);
        this.product = product;
    }
    
    public void build()
    {
        System.out.println("rodou");
        lblName = new JLabel();
        lblValue = new JLabel();
        lblDescription = new JLabel();
        txtName = new JTextField();
        txtValue = new JTextField();
        txtDescription = new JTextField();
        btnConfirm = new JButton();
        
        lblName.setText("Nome : ");
        lblDescription.setText("Descrição : ");
        lblValue.setText("Valor : ");
        btnConfirm.setText("Confirmar");
        
        lblName.setBounds(10,10, 100,50);
        lblDescription.setBounds(10,50,100,50);
        lblValue.setBounds(10,90,100,50);
        btnConfirm.setBounds(10, 150, 200, 25);
        
        txtName.setBounds(100, 25, 100, 20);
        txtDescription.setBounds(100, 65, 100, 20);
        txtValue.setBounds(100, 105, 95, 20);
        
        frame.add(lblName);
        frame.add(lblDescription);
        frame.add(lblValue);
        frame.add(txtName);
        frame.add(txtValue);
        frame.add(txtDescription);
        frame.add(btnConfirm);
        frame.setSize(250,250);
        frame.setLayout(null);
        frame.setVisible(true);
        
        if (product != null)
        {
            txtName.setText(product.getName());
            txtValue.setText(Float.toString(product.getValue()));
            txtDescription.setText(product.getDescription());
        }

        btnConfirm.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (product == null)
                {
                    try {
                        Gson gson = new Gson();
                        CreateProductRequestDTO createProductRequest = new CreateProductRequestDTO();
                        createProductRequest.setOp(800);
                        createProductRequest.setProductName(txtName.getText());
                        createProductRequest.setDescription(txtDescription.getText());
                        createProductRequest.setProductValue(Float.parseFloat(txtValue.getText()));

                        System.out.printf("\nMensagem Enviada para o Server : " + gson.toJson(createProductRequest) + "\n");
                        middleware.println(gson.toJson(createProductRequest));
                        String resposta = middleware.readLine();
                        System.out.println("Servidor respondeu : " + resposta);  
                    }
                    catch (IOException ex)
                    {
                        Logger.getLogger(ClientInterface.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else
                {
                    try {
                        Gson gson = new Gson();
                        EditProductRequestDTO editProductRequest = new EditProductRequestDTO();
                        editProductRequest.setOp(900);
                        editProductRequest.setProductName(txtName.getText());
                        editProductRequest.setDescription(txtDescription.getText());
                        editProductRequest.setProductValue(Float.parseFloat(txtValue.getText()));
                        editProductRequest.setProductId(product.getId());

                        System.out.printf("\nMensagem Enviada para o Server : " + gson.toJson(editProductRequest) + "\n");
                        middleware.println(gson.toJson(editProductRequest));
                        String resposta = middleware.readLine();
                        System.out.println("Servidor respondeu : " + resposta);  
                    }
                    catch (IOException ex)
                    {
                        Logger.getLogger(ClientInterface.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

    }
    
}
