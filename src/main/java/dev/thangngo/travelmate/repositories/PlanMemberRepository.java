package dev.thangngo.travelmate.repositories;

import dev.thangngo.travelmate.entities.Plan;
import dev.thangngo.travelmate.entities.PlanMember;
import dev.thangngo.travelmate.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlanMemberRepository extends JpaRepository<PlanMember, Long> {
    boolean existsByUserAndPlan(User user, Plan plan);
    Optional<PlanMember> findByUserIdAndPlanId(UUID userId, Long planId);
    List<PlanMember> findAllByPlanId(Long planId);

    @Query("SELECT COUNT(pm) FROM PlanMember pm WHERE pm.plan.id = :planId")
    int countByPlanId(@Param("planId") Long planId);
}
