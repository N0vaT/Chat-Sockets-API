package edu.school21.sockets.services;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessagesServiceImpl implements MessagesService {
    private MessagesRepository messagesRepository;

    @Autowired
    public MessagesServiceImpl(MessagesRepository messagesRepository) {
        this.messagesRepository = messagesRepository;
    }

    @Override
    public List<Message> findAll() {
        return messagesRepository.findAll();
    }

    @Override
    public void save(User user, String text) {
        Message message = new Message();
        message.setMessageText(text);
        message.setMessageTime(LocalDateTime.now());
        message.setMessageSender(user);
        messagesRepository.save(message);
    }
}
