package dev.thangngo.travelmate.dtos.request.planmember;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlanMemberCreateRequest {

    @NotNull
    private Long planId;

    @NotNull
    private String role;

    @Email
    @NotNull
    private String email;
}
