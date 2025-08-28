package dev.thangngo.travelmate.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class PlanResponse {
    private Long id;
    private String name;
    private UserResponse owner;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate startDate;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate endDate;

    private String location;

    private String description;

    private boolean isPublic;

    private String slug;

    private Instant createdAt;

    private Instant updatedAt;
}
