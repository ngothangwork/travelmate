package dev.thangngo.travelmate.repositories;

import dev.thangngo.travelmate.entities.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    Plan getPlansByName(String name);
    List<Plan> getPlansByUserId(UUID id);
    Optional<Plan> findByIdAndSlug(Long id, String slug);
}

