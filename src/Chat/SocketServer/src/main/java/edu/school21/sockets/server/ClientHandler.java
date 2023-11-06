package edu.school21.sockets.server;

import edu.school21.sockets.exceptions.InvalidCommandException;
import edu.school21.sockets.models.User;
import edu.school21.sockets.services.MessagesService;
import edu.school21.sockets.services.UsersService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public class ClientHandler implements Runnable{
    private final Server server;
    private final Socket clientSocket;
    private final UsersService usersService;
    private final MessagesService messagesService;
    private String clientHandlerName;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private User user;
    private final String WELCOME_MESSAGE = "Hello from Server!\nregistration\nsignUp";
    private final String COMMAND_SIGN_UP = "signUp";
    private final String COMMAND_REGISTRATION = "registration";
    private final String REQUEST_ENTER_USERNAME = "Enter username:";
    private final String REQUEST_ENTER_PASSWORD = "Enter password:";
    private final String REQUEST_START_MESSAGING = "Start messaging";
    private final String REQUEST_SUCCESS = "Successful!";
    private final String REQUEST_END = "END";

    public ClientHandler(Server server, Socket clientSocket, UsersService usersService, MessagesService messagesService) {
        this.server = server;
        this.clientSocket = clientSocket;
        this.usersService = usersService;
        this.messagesService = messagesService;
        try{
            this.objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            this.objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            sendMsg(WELCOME_MESSAGE);
            String clientCommand = (String) objectInputStream.readObject();
            parseCommand(clientCommand);
        } catch (IOException | ClassNotFoundException | InvalidCommandException e) {
            throw new RuntimeException(e);
        }
    }

    private void parseCommand(String command){
        switch (command){
            case COMMAND_SIGN_UP:
                singUp();
                break;
            case COMMAND_REGISTRATION:
                registration();
                break;
            default:
                String ex = "Invalid command - " + command;
                sendMsg(ex);
                sendMsg(REQUEST_END);
                throw new InvalidCommandException(ex);
        }
    }
    private void registration(){
        try {
            sendMsg(REQUEST_ENTER_USERNAME);
            String userName = (String) objectInputStream.readObject();
            sendMsg(REQUEST_ENTER_PASSWORD);
            String password = (String) objectInputStream.readObject();
            sendMsg(REQUEST_SUCCESS);
            User user = new User(userName, password);
            usersService.save(user);
            System.out.println("User - " + userName + " registered " + LocalDateTime.now());
            sendMsg(REQUEST_END);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void singUp(){
        try {
            sendMsg(REQUEST_ENTER_USERNAME);
            String userName = (String) objectInputStream.readObject();
            sendMsg(REQUEST_ENTER_PASSWORD);
            Optional<User> userForName = usersService.findByUserName(userName);
            if(!userForName.isPresent()){
                sendMsg("User with name - " + userName + " not found.");
                sendMsg(REQUEST_END);
                return;
            }
            String password = (String) objectInputStream.readObject();
            user = userForName.get();
            if(!usersService.checkPassword(password, user.getPassword())){
                sendMsg("Password - " + password + " is incorrect.");
                sendMsg(REQUEST_END);
                return;
            }
            clientHandlerName = userName;
            server.addClient(this);
            sendMsg(REQUEST_START_MESSAGING);
            messagesService.findAll().stream().forEach(s->sendMsg(s.getMessageSender().getUserName() + ": " + s.getMessageText()));
            messaging();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void messaging(){
        String clientMessage;
        try {
            while (true){
                clientMessage = (String) objectInputStream.readObject();
                if(REQUEST_END.equals(clientMessage)){
                    sendMsg(REQUEST_END);
                    server.removeUser(this);
                    break;
                }
                messagesService.save(user, clientMessage);
                server.broadcast(clientHandlerName + ": " + clientMessage, this);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMsg(String msg) {
        try {
            objectOutputStream.writeObject(msg);
            objectOutputStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientHandler that = (ClientHandler) o;
        return Objects.equals(clientHandlerName, that.clientHandlerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientHandlerName);
    }

    public String getClientHandlerName() {
        return clientHandlerName;
    }
}
