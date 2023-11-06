package edu.school21.sockets.services;

import edu.school21.sockets.models.User;

import java.util.List;
import java.util.Optional;

public interface UsersService {
    Optional<User> findById(Long id);
    List<User> findAll();
    void save(User entity);
    void update(User entity);
    void delete(Long id);
    Optional<User> findByUserName(String userName);
    boolean checkPassword(String password, String encrypted);
}
