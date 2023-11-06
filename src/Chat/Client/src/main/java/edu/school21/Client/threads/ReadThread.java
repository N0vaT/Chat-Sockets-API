package edu.school21.Client.threads;

import edu.school21.Client.models.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ReadThread implements Runnable{
    private final Socket socket;
    private final Client client;
    private ObjectInputStream objectInputStream;

    public ReadThread(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;
        try{
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String inputMessage = "";
        while(true){
            try {
                if(!socket.isClosed()){
                    inputMessage = (String)objectInputStream.readObject();
                }else{
                    break;
                }
                if("END".equals(inputMessage)){
                    socket.close();
                    break;
                }
                System.out.println(inputMessage);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException("Connection interrupted");
            }
        }
    }
}
