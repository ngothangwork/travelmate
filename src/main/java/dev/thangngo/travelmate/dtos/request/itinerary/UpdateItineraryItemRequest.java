package dev.thangngo.travelmate.dtos.request.itinerary;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class UpdateItineraryItemRequest {
    private String title;
    private String description;
    private String location;
    private Boolean isDone;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;
}

