/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.*;
import java.net.Socket;
import DTOs.requests.*;
import DTOs.responses.DefaultResponse;
import DTOs.responses.ErrorResponse;
import DTOs.responses.ListProductsResponse;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JList;
import models.Product;
import models.User;
import services.ProductService;
import services.UserService;

/**
 *
 * @author madeinweb
 */
public class ServerThread extends Thread
{
    private Socket socket;
    private UserService userService;
    private ProductService productService;
    private User loggedUser;
    
    public ServerThread(Socket socket, ProductService productService, UserService userService, JList listAllUsers, JList listOnlineUsers)
    {
        this.socket = socket;
        this.userService = userService;
        this.productService = productService;
    }
    
    public void run()
    {
        try 
        {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            
            String jsonRequest;
            
            do
            {
                jsonRequest = reader.readLine();
                System.out.println("Cliente Enviou : "+jsonRequest);
                String response = TreatRequest(jsonRequest);
                writer.println(response);
            } while(!jsonRequest.equals("Bye"));
            
            System.out.println("ENCERROU");
            socket.close();
        } 
        catch(IOException ex) 
        {
            System.out.println("Encerramento forçado de " + socket.getLocalAddress()); 
            if (loggedUser != null)
                userService.logout(loggedUser.getUsername()); 
        }
        catch(Exception ex)
        {
            System.out.println("Erro na request de " + socket.getLocalAddress());
        }
    }
    
    public String TreatRequest(String jsonRequest)
    {
        Gson gson = new Gson();
        try
        {
            DefaultRequest request = gson.fromJson(jsonRequest, DefaultRequest.class);
            
            boolean result;
            
            switch (request.getOp())
            {
                case 100:
                    LoginRequestDTO loginRequest = gson.fromJson(jsonRequest, LoginRequestDTO.class);
                    User user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
                    if (user != null)
                    {
                        loggedUser = user;
                        return gson.toJson(new DefaultResponse(101), DefaultResponse.class);
                    }
                    return gson.toJson(new DefaultResponse(102), DefaultResponse.class);
                case 200:
                    LogoutRequestDTO logoutRequest = gson.fromJson(jsonRequest, LogoutRequestDTO.class);
                    userService.logout(logoutRequest.getUsername());
                    return gson.toJson(new DefaultResponse(201));
                case 300:
                    RegisterRequestDTO registerRequest = gson.fromJson(jsonRequest, RegisterRequestDTO.class);
                    result = userService.register(registerRequest.getUsername(), registerRequest.getPassword());
                    if (result)
                        return gson.toJson(new DefaultResponse(301));
                    return gson.toJson(new ErrorResponse(302, "Erro no Cadastro."));
                case 400:
                    ListProductsRequestDTO listAllRequest = gson.fromJson(jsonRequest, ListProductsRequestDTO.class);
                    List<Product> allProducts = productService.listAll();
                    ListProductsResponse listAllResponse = new ListProductsResponse(401, allProducts);
                    return gson.toJson(listAllResponse);
                case 500:
                    ListProductsRequestDTO listProductUserRequest = gson.fromJson(jsonRequest, ListProductsRequestDTO.class);
                    System.out.println("Usuario logado id : " + loggedUser.getUsername());
                    List<Product> userProducts = productService.listByUser(loggedUser.getUsername());
                    ListProductsResponse listUserProductsResponse = new ListProductsResponse(501, userProducts);
                    return gson.toJson(listUserProductsResponse);
                case 800:
                    CreateProductRequestDTO createProductRequest = gson.fromJson(jsonRequest, CreateProductRequestDTO.class);
                    result = productService.createProduct(
                            createProductRequest.getProductName(), 
                            createProductRequest.getDescription(), 
                            createProductRequest.getProductValue(), 
                            loggedUser.getUsername());
                    if (result)
                        return gson.toJson(new DefaultResponse(801));
                    return gson.toJson(new ErrorResponse(802, "Erro ao criar produto"));     
                case 900:
                   EditProductRequestDTO editProductRequest = gson.fromJson(jsonRequest, EditProductRequestDTO.class);
                   result = productService.editProduct(
                           editProductRequest.getProductName(), 
                           editProductRequest.getDescription(), 
                           editProductRequest.getProductValue(), 
                           editProductRequest.getProductId());
                   if (result)
                       return gson.toJson(new DefaultResponse(901));
                   return gson.toJson(new ErrorResponse(902, "Produto nao encontrado"));
                case 1000:
                   DeleteProductRequestDTO deleteProductRequest = gson.fromJson(jsonRequest, DeleteProductRequestDTO.class);
                   result = productService.deleteProduct(deleteProductRequest.getProductId());
                   if (result)
                       return gson.toJson(new DefaultResponse(1001));
                   return gson.toJson(new ErrorResponse(1002, "Produto nao encontrado"));
                default: 
                    return gson.toJson(new DefaultResponse(999), DefaultResponse.class);
            }
        }
        catch(JsonSyntaxException ex)
        {
            System.out.println("Erro ao converter para Json");
            return "";
        }
    }
    
}
