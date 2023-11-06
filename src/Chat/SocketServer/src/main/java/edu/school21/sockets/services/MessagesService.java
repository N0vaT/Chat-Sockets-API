package edu.school21.sockets.services;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;

import java.util.List;

public interface MessagesService {
    List<Message> findAll();
    void save(User user, String text);
}
