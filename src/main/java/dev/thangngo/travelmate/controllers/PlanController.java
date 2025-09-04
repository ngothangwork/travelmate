package dev.thangngo.travelmate.controllers;


import dev.thangngo.travelmate.dtos.request.plan.PlanCreateRequest;
import dev.thangngo.travelmate.dtos.request.plan.PlanUpdateRequest;
import dev.thangngo.travelmate.dtos.response.ApiResponse;
import dev.thangngo.travelmate.dtos.response.plan.ListPlanResponse;
import dev.thangngo.travelmate.dtos.response.plan.PlanResponse;
import dev.thangngo.travelmate.services.PlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/plans")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ListPlanResponse>>> getAllPlans() {
        List<ListPlanResponse> plans = planService.getAllPlans();
        return ResponseEntity.ok(ApiResponse.<List<ListPlanResponse>>builder()
                .success(true)
                .message("Get all plans successfully")
                .result(plans)
                .build());
    }

    @GetMapping("/{id:\\d+}-{slug}")
    public ResponseEntity<ApiResponse<PlanResponse>> getPlanById(
            @PathVariable Long id,
            @PathVariable String slug) {
        PlanResponse plan = planService.findByIdAndSlug(id, slug);
        return ResponseEntity.ok(ApiResponse.<PlanResponse>builder()
                .success(true)
                .message("Get plan successfully")
                .result(plan)
                .build());
    }


    @PostMapping
    public ResponseEntity<ApiResponse<PlanResponse>> createPlan(@RequestBody PlanCreateRequest planCreateRequest) {
        PlanResponse plan = planService.createPlan(planCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<PlanResponse>builder()
                        .success(true)
                        .message("Plan created successfully")
                        .result(plan)
                        .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PlanResponse>> updatePlan(
            @PathVariable Long id,
            @RequestBody PlanUpdateRequest planUpdateRequest) {
        PlanResponse plan = planService.updatePlan(id, planUpdateRequest);
        return ResponseEntity.ok(ApiResponse.<PlanResponse>builder()
                .success(true)
                .message("Plan updated successfully")
                .result(plan)
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePlan(@PathVariable Long id) {
        planService.deletePlan(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId:\\d+}")
    public ResponseEntity<ApiResponse<List<PlanResponse>>> getPlansByUserId(@PathVariable UUID userId) {
        List<PlanResponse> plans = planService.getPlansByUserId(userId);
        return ResponseEntity.ok(ApiResponse.<List<PlanResponse>>builder()
                .success(true)
                .message("Get plans by user id successfully")
                .result(plans)
                .build());
    }
}
