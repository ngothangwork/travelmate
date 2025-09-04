package dev.thangngo.travelmate.dtos.response.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
    private UUID id;
    private String email;
    private String passwordHash;
    private String name;
    private String avatar;
    private String role;
    private Long points;
}
