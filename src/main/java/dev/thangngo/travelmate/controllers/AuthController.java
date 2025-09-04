package dev.thangngo.travelmate.controllers;

import dev.thangngo.travelmate.common.email.EmailHelper;
import dev.thangngo.travelmate.dtos.response.user.UserResponse;
import dev.thangngo.travelmate.enums.UserRole;
import dev.thangngo.travelmate.sercurities.JwtUtil;
import dev.thangngo.travelmate.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final EmailHelper emailHelper;

    @NonFinal
    @Value("${admin.password}")
    private String password;

    @PostMapping("/google")
    public ResponseEntity<?> loginWithGoogle(@RequestBody Map<String, String> payload) {
        String idToken = payload.get("credential");
        if (idToken == null) {
            return ResponseEntity.badRequest().body("Missing credential");
        }

        String googleApi = "https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken;
        RestTemplate restTemplate = new RestTemplate();
        Map googleUser = restTemplate.getForObject(googleApi, Map.class);

        if (googleUser == null || googleUser.get("email") == null) {
            return ResponseEntity.status(401).body("Invalid Google token");
        }

        String email = (String) googleUser.get("email");
        String name = (String) googleUser.get("name");
        String avatar = (String) googleUser.get("picture");

        UserResponse existingUser = userService.getUserByEmail(email);
        if (existingUser == null) {
            UserResponse newUser = new UserResponse();
            newUser.setEmail(email);
            newUser.setName(name);
            newUser.setAvatar(avatar);
            newUser.setPasswordHash(passwordEncoder.encode(password));
            newUser.setRole(UserRole.USER.toString());
            newUser.setPoints(0L);
            existingUser = userService.createUser(newUser);
        }

        String jwtToken = jwtUtil.generateToken(email);

 //       emailHelper.sendSimpleEmail(email, "TRAVEL-MATE: ĐĂNG NHẬP THÀNH CÔNG", "Cảm ơn bạn đã sử dụng hệ thống của chúng tôi, hiện nay hệ thống đang trong quá trình phát triển, mọi thắc mắc hoặc vấn đề hãy liên hệ hotline: 0398347747");

        return ResponseEntity.ok(Map.of(
                "token", jwtToken,
                "user", existingUser
        ));
    }
}
