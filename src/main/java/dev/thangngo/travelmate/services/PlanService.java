package dev.thangngo.travelmate.services;

import dev.thangngo.travelmate.dtos.request.plan.PlanCreateRequest;
import dev.thangngo.travelmate.dtos.request.plan.PlanUpdateRequest;
import dev.thangngo.travelmate.dtos.response.plan.ListPlanResponse;
import dev.thangngo.travelmate.dtos.response.plan.PlanResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlanService {
    PlanResponse getPlanByName(String name);
    List<ListPlanResponse> getAllPlans();
    PlanResponse findByIdAndSlug(Long id, String slug);
    PlanResponse createPlan(PlanCreateRequest planCreateRequest);
    PlanResponse updatePlan(Long id, PlanUpdateRequest planRequest);
    void deletePlan(Long id);
    List<PlanResponse> getPlansByUserId(UUID userId);
    Optional<PlanResponse> getPlanById(Long id);
}

