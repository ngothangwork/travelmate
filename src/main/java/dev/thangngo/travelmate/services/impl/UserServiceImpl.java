package dev.thangngo.travelmate.services.impl;

import dev.thangngo.travelmate.dtos.response.user.UserResponse;
import dev.thangngo.travelmate.entities.User;
import dev.thangngo.travelmate.mappers.UserMapper;
import dev.thangngo.travelmate.repositories.UserRepository;
import dev.thangngo.travelmate.services.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toUserResponse)
                .orElse(null);
    }

    @Override
    public UserResponse createUser(UserResponse userResponse) {
        User user = userMapper.toUser(userResponse);
        user.setPasswordHash(userResponse.getPasswordHash());
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());

        User saved = userRepository.save(user);
        return userMapper.toUserResponse(saved);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    @Override
    public boolean updateUser(String email, UserResponse userResponse) {
        return userRepository.findByEmail(email)
                .map(existingUser -> {
                    existingUser.setName(userResponse.getName());
                    existingUser.setAvatar(userResponse.getAvatar());
                    existingUser.setPasswordHash(userResponse.getPasswordHash());
                    existingUser.setUpdatedAt(Instant.now());
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
    }

    @Override
    public boolean deleteUser(String email) {
        return userRepository.findByEmail(email)
                .map(user -> {
                    userRepository.delete(user);
                    return true;
                }).orElse(false);
    }
}
