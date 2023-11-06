package edu.school21.sockets.models;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MessageMapper implements RowMapper<Message> {
    @Override
    public Message mapRow(ResultSet resultSet, int i) throws SQLException {
        Message message = new Message();
        message.setMessageId(resultSet.getLong("message_id"));
        message.setMessageText(resultSet.getString("message_text"));
        message.setMessageTime(resultSet.getTimestamp("message_time").toLocalDateTime());
        User user = new User(resultSet.getLong("user_id"),
                resultSet.getString("user_name"),
                resultSet.getString("user_password"));
        message.setMessageSender(user);
        return message;
    }
}
