package dev.thangngo.travelmate.dtos.response.itinerary;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
public class ItineraryItemResponse implements Serializable {
    private Long id;
    private Long planId;
    private UUID createdById;
    private LocalDate date;
    private String title;
    private String description;
    private String location;
    private LocalTime startTime;
    private LocalTime endTime;
    private Boolean isDone;
}
