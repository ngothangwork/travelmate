package dev.thangngo.travelmate.dtos.request;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlanMemberRequest {
    Long planId;
    String role;
    @Email
    String email;
}
