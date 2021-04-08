import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author elieba
 */
public class Server {

    ServerSocket servSock;

    public Server() {
        try {
            servSock = new ServerSocket(10000);
            while (true) {
                Socket s = servSock.accept();
                new ChatHandler(s);
            }
        } catch (IOException e) {

        }
    }

    public static void main(String[] args) {
        new Server();
    }
}

class ChatHandler extends Thread {

    DataInputStream dis;
    PrintStream ps;
    static Vector<ChatHandler> clientsVector = new Vector<ChatHandler>();

    public ChatHandler(Socket s) {
        try {
            dis = new DataInputStream(s.getInputStream());
            ps = new PrintStream(s.getOutputStream());
            clientsVector.add(this);
            start();
        } catch (IOException e) {
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                String msg = dis.readLine();
                System.out.println(msg);
                if (msg == null) {
                    clientsVector.remove(this);
                    break;

                }

                sendToAllClients(msg);
                
            } catch (IOException e) {
                clientsVector.remove(this);
            }
        }
    }

    void sendToAllClients(String move) {
        for (ChatHandler ch : clientsVector) {
         
            ch.ps.println(move);
        }
    }
}
