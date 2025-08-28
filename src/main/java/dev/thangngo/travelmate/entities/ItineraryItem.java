package dev.thangngo.travelmate.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "itinerary_item")
public class ItineraryItem {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    @NotNull
    @Column(name = "\"date\"", nullable = false)
    private LocalDate date;

    @NotNull
    @Nationalized
    @Column(name = "title", nullable = false, columnDefinition = "NVARCHAR(MAX)")
    private String title;

    @Nationalized
    @Lob
    @Column(name = "description", columnDefinition = "NVARCHAR(MAX)")
    private String description;

    @Size(max = 255)
    @Nationalized
    @Column(name = "location")
    private String location;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

}