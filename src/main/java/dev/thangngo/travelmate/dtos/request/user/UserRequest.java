package dev.thangngo.travelmate.dtos.request.user;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class UserRequest {
    private UUID id;

    @Email
    private String email;

    private String passwordHash;

    private String name;

    private String avatar;
}
