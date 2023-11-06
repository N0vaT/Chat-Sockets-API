package edu.school21.sockets.server;

import edu.school21.sockets.services.MessagesService;
import edu.school21.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class Server {
    private int port = 8081;
    private final UsersService usersService;
    private final MessagesService messagesService;
    private final ExecutorService threadPool = Executors.newFixedThreadPool(10);
    private final Set<ClientHandler> clientHandlers = new HashSet<>();
    @Autowired
    public Server(UsersService usersService, MessagesService messagesService) {
        this.usersService = usersService;
        this.messagesService = messagesService;
    }

    public void serverStart(int port){
        System.out.println("Start server " + LocalDateTime.now());
        this.port = port;
        try(ServerSocket serverSocket = new ServerSocket(port)){
            while(true){
                Socket clientSocket = serverSocket.accept();
                ClientHandler newClient = new ClientHandler(this, clientSocket, usersService, messagesService);
                threadPool.submit(newClient);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    void addClient(ClientHandler client) {
        clientHandlers.add(client);
        System.out.println("User - " + client.getClientHandlerName() + " added to server " + LocalDateTime.now());
    }
    void broadcast(String message, ClientHandler clientHandler) {
        for(ClientHandler client : clientHandlers){
            if(client != clientHandler){
                client.sendMsg(message);
            }
        }
    }

    void removeUser(ClientHandler client) {
        clientHandlers.remove(client);
        System.out.println("User - " + client.getClientHandlerName() + " left the server " + LocalDateTime.now());
    }


}
