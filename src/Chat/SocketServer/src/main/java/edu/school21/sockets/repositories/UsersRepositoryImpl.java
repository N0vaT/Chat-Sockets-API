package edu.school21.sockets.repositories;

import edu.school21.sockets.models.User;
import edu.school21.sockets.models.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class UsersRepositoryImpl implements UsersRepository{
    private final JdbcTemplate jdbcTemplate;
    private final UserMapper userMapper;
    private final String FIND_BY_ID_QUERY = "SELECT user_id, user_name, user_password FROM user_table WHERE user_id = ?";
    private final String FIND_BY_USERNAME_QUERY = "SELECT user_id, user_name, user_password FROM user_table WHERE user_name = ?";
    private final String FIND_ALL_QUERY = "SELECT user_id, user_name, user_password FROM user_table";
    private final String SAVE_QUERY = "INSERT INTO user_table(user_name, user_password) VALUES(?, ?)";
    private final String UPDATE_QUERY = "UPDATE user_table SET user_name = ?, user_password = ? WHERE user_id = ?";
    private final String DELETE_QUERY = "DELETE user_table WHERE user_id = ?";

    @Autowired
    public UsersRepositoryImpl(DataSource dataSource, UserMapper userMapper) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.userMapper = userMapper;
    }

    @Override
    public Optional<User> findById(Long id) {
        User user = jdbcTemplate.queryForObject(FIND_BY_ID_QUERY, new Object[]{id}, userMapper);
        return Optional.ofNullable(user);

    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, userMapper);
    }

    @Override
    public void save(User entity) {
        jdbcTemplate.update(SAVE_QUERY, entity.getUserName(), entity.getPassword());
    }

    @Override
    public void update(User entity) {
        jdbcTemplate.update(UPDATE_QUERY, entity.getUserName(), entity.getPassword(), entity.getUserId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE_QUERY, id);
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        try{
            User user = jdbcTemplate.queryForObject(FIND_BY_USERNAME_QUERY, new Object[]{userName}, userMapper);
            return Optional.ofNullable(user);
        }catch (DataAccessException ex){
            return Optional.empty();
        }
    }
}
