package edu.school21.Client.models;

import edu.school21.Client.threads.ReadThread;
import edu.school21.Client.threads.WriteThread;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private String hostname;
    private int port;
    private String username;
    public Client(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void execute(){
        try{
            Socket socket = new Socket(hostname, port);
            Thread threadWrite = new Thread(new WriteThread(socket, this));
            Thread threadRead = new Thread(new ReadThread(socket, this));
            threadRead.start();
            threadWrite.start();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
