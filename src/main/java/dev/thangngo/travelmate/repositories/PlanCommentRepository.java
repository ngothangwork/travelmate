package dev.thangngo.travelmate.repositories;

import dev.thangngo.travelmate.entities.PlanComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanCommentRepository extends JpaRepository<PlanComment, Long> {
}
