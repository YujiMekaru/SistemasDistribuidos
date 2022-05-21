/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JFrame;

/**
 *
 * @author madeinweb
 */
public class DefaultScreen {
    protected JFrame frame;
    protected BufferedReader in;
    protected PrintWriter out;
    protected Socket socket;
    
    public DefaultScreen(JFrame frame, BufferedReader in, PrintWriter out, Socket socket)
    {
        this.frame = frame;
        this.in = in;
        this.out = out;
        this.socket = socket;
    }
}
