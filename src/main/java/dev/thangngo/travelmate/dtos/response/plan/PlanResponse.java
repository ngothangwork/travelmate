package dev.thangngo.travelmate.dtos.response.plan;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.thangngo.travelmate.dtos.response.user.UserResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class PlanResponse implements Serializable {
    private Long id;
    private String name;
    private UserResponse owner;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate startDate;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate endDate;

    private String location;

    private String description;

    private Boolean isPublic;

    private String slug;

    private Instant createdAt;

    private Instant updatedAt;
}
