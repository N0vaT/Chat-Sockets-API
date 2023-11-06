package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class MessagesRepositoryImpl implements MessagesRepository{
    private final JdbcTemplate jdbcTemplate;
    private final MessageMapper messageMapper;
    private final String FIND_ALL_QUERY = "SELECT message_id, message_text, message_time, message_sender, user_id, user_name, user_password FROM message_table\n" +
            "JOIN user_table ON user_table.user_id = message_table.message_sender";
    private final String SAVE_QUERY = "INSERT INTO message_table(message_text, message_time, message_sender) VALUES(?, ?, ?)";

    @Autowired
    public MessagesRepositoryImpl(DataSource dataSource, MessageMapper messageMapper) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.messageMapper = messageMapper;
    }

    @Override
    public Optional<Message> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Message> findAll() {
        List<Message> messages = jdbcTemplate.query(FIND_ALL_QUERY, messageMapper);
        return messages;
    }

    @Override
    public void save(Message entity) {
        jdbcTemplate.update(SAVE_QUERY, entity.getMessageText(), entity.getMessageTime(), entity.getMessageSender().getUserId());
    }

    @Override
    public void update(Message entity) {

    }

    @Override
    public void delete(Long id) {

    }
}
