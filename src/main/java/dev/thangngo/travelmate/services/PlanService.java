package dev.thangngo.travelmate.services;

import dev.thangngo.travelmate.dtos.request.plan.PlanRequest;
import dev.thangngo.travelmate.dtos.response.plan.ListPlanResponse;
import dev.thangngo.travelmate.dtos.response.plan.PlanResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlanService {
    PlanResponse getPlanByName(String name);
    List<ListPlanResponse> getAllPlans();
    PlanResponse findByIdAndSlug(Long id, String slug);
    PlanResponse createPlan(PlanRequest planRequest);
    PlanResponse updatePlan(Long id, PlanRequest planRequest);
    void deletePlan(Long id);
    List<PlanResponse> getPlansByUserId(UUID userId);
    Optional<PlanResponse> getPlanById(Long id);
}

