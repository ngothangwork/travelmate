package dev.thangngo.travelmate.dtos.request.itinerary;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CreateItineraryItemRequest {
    @NotNull
    private Long planId;

    @NotNull
    private UUID createdBy;

    @NotNull
    private LocalDate date;

    @NotBlank
    private String title;

    private String description;
    private String location;
    private Boolean isDone;
    private LocalTime startTime;
    private LocalTime endTime;
}

