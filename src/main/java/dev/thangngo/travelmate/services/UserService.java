package dev.thangngo.travelmate.services;

import dev.thangngo.travelmate.dtos.response.user.UserResponse;

import java.util.List;


public interface UserService {
    UserResponse createUser(UserResponse userResponse);
    List<UserResponse> getAllUsers();
    UserResponse getUserByEmail(String email);
    boolean updateUser(String email, UserResponse userResponse);
    boolean deleteUser(String email);
}
