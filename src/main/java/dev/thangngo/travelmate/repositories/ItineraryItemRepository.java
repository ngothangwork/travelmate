package dev.thangngo.travelmate.repositories;

import dev.thangngo.travelmate.entities.ItineraryItem;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ItineraryItemRepository extends JpaRepository<ItineraryItem, Long> {
    List<ItineraryItem> findByPlanId(Long planId);
    List<ItineraryItem> findByPlanIdAndDate(Long plan_id, @NotNull LocalDate date);
}
