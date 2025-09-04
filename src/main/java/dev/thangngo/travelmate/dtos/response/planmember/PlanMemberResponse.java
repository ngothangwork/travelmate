package dev.thangngo.travelmate.dtos.response.planmember;

import dev.thangngo.travelmate.enums.MemberRole;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class PlanMemberResponse implements Serializable {
    private Long id;
    private UUID userId;
    private String email;
    private String name;
    private String avatar;
    private MemberRole role;
}
