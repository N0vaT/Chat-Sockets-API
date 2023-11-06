package edu.school21.sockets.models;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getLong("user_id"));
        user.setUserName(resultSet.getString("user_name"));
        user.setPassword(resultSet.getString("user_password"));
        return user;
    }
}
