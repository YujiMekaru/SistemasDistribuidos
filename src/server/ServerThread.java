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
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JList;
import models.User;
import services.NoDbUserService;
import services.UserService;

/**
 *
 * @author madeinweb
 */
public class ServerThread extends Thread
{
    private Socket socket;
    private NoDbUserService userService;
    
    public ServerThread(Socket socket, NoDbUserService userService, JList listAllUsers, JList listOnlineUsers)
    {
        this.socket = socket;
        this.userService = userService;
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
                    result = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
                    if (result)
                        return gson.toJson(new DefaultResponse(101), DefaultResponse.class);
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
                    return gson.toJson(new DefaultResponse(302));
                        
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
