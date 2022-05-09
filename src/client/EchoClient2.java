package client;

import DTOs.requests.*;
import com.google.gson.*;
import java.io.*;
import java.net.*;

public class EchoClient2 {
    public static void main(String[] args) throws IOException {

        String serverHostname = new String ("127.0.0.1");
        if (args.length > 0)
           serverHostname = args[0];
        System.out.println ("Attemping to connect to host " +
                serverHostname + " on port 10009.");

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            echoSocket = new Socket(serverHostname, 10009);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                                        echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + serverHostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                               + "the connection to: " + serverHostname);
            System.exit(1);
        }

	BufferedReader stdIn = new BufferedReader(
                                   new InputStreamReader(System.in));
	String userInput;

        System.out.println ("Type Message (\"Bye.\" to quit)");
        System.out.println("1- Login | 2- Logout | 3- Register");
	while ((userInput = stdIn.readLine()) != null) 
           {
              Gson gson = new Gson();
              System.out.println("1- Login | 2- Logout | 3- Register");
//            
//            loginRequest.setUsername(userInput);
//            loginRequest.setPassword("senhateste");
//            
//            System.out.println(gson.toJson(loginRequest));
//	    out.println(gson.toJson(loginRequest));
                if (userInput.equals("Bye."))
                break;

                String username;
                String password;
               switch (Integer.parseInt(userInput))
               {
                    case 1:
                        LoginRequestDTO loginRequest = new LoginRequestDTO();
                        loginRequest.setOp(100);
                        System.out.println("Username : ");
                        username = stdIn.readLine();
                        loginRequest.setUsername(username);
                        System.out.println("Password : ");
                        password = stdIn.readLine();
                        loginRequest.setPassword(password);
                        System.out.printf("\n\nMensagem Enviada para o Server : "+gson.toJson(loginRequest)+"\n\n");
                        out.println(gson.toJson(loginRequest));
                        break;
                    case 2:
                        LogoutRequestDTO logoutRequest = new LogoutRequestDTO();
                        logoutRequest.setOp(200);
                        System.out.println("Username : ");
                        username = stdIn.readLine();
                        logoutRequest.setUsername(username);
                        System.out.printf("\n\nMensagem Enviada para o Server : "+gson.toJson(logoutRequest)+"\n\n");
                        out.println(gson.toJson(logoutRequest));
                        break; 
                    case 3:
                        RegisterRequestDTO registerRequest = new RegisterRequestDTO();
                        registerRequest.setOp(300);
                        System.out.println("Username : ");
                        username = stdIn.readLine();
                        registerRequest.setUsername(username);
                        System.out.println("Password : ");
                        password = stdIn.readLine();
                        registerRequest.setPassword(password);
                        System.out.printf("\n\nMensagem Enviada para o Server : "+gson.toJson(registerRequest)+"\n\n");
                        out.println(gson.toJson(registerRequest));                        
                    default:
                        System.out.println("Opção Invalida");
               }
            // end loop


	    System.out.println("echo: " + in.readLine());
	   }

	out.close();
	in.close();
	stdIn.close();
	echoSocket.close();
    }
}

