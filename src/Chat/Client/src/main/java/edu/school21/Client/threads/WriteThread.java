package edu.school21.Client.threads;


import edu.school21.Client.models.Client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class WriteThread implements Runnable{
    private final Socket socket;
    private ObjectOutputStream writer;
    private final Client client;

    public WriteThread(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;
        try {
            writer = new ObjectOutputStream(socket.getOutputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String str = "";
        while (true) {
            try {
                str = scanner.nextLine();
                if (!socket.isClosed()) {
                    writer.writeObject(str);
                }else{
                    break;
                }
                if ("END".equals(str)) {
                    break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
