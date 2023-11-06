package edu.school21.sockets.services;

import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService{

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> findById(Long id) {
        return usersRepository.findById(id);

    }

    @Override
    public List<User> findAll() {
        return usersRepository.findAll();
    }

    @Override
    public void save(User entity) {
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        usersRepository.save(entity);
    }

    @Override
    public void update(User entity) {
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        usersRepository.update(entity);
    }

    @Override
    public void delete(Long id) {
        usersRepository.delete(id);
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        return usersRepository.findByUserName(userName);
    }

    @Override
    public boolean checkPassword(String password, String encrypted) {
        return passwordEncoder.matches(password, encrypted);
    }
}
