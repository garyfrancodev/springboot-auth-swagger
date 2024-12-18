package com.garyfrancodev.auth.service;

import com.garyfrancodev.auth.model.User;
import com.garyfrancodev.auth.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
       return userRepository.save(user);
    }

    public Optional<User> readUser(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(Long id, User userUpdate) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(userUpdate.getUsername());
        user.setPassword(userUpdate.getPassword());
        user.setEmail(userUpdate.getEmail());

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


}
