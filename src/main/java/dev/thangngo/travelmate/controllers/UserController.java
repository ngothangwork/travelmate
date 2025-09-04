package dev.thangngo.travelmate.controllers;

import dev.thangngo.travelmate.dtos.response.ApiResponse;
import dev.thangngo.travelmate.dtos.response.user.UserResponse;
import dev.thangngo.travelmate.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{email}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserByEmail(@PathVariable String email) {
        UserResponse user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity
                    .status(404)
                    .body(ApiResponse.<UserResponse>builder()
                            .code(1001)
                            .message("User not found")
                            .build());
        }
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .success(true)
                .message("Success")
                .result(user)
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.<List<UserResponse>>builder()
                .success(true)
                .message("Success")
                .result(users)
                .build());
    }

}
