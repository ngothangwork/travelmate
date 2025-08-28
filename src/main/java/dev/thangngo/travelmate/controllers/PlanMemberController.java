package dev.thangngo.travelmate.controllers;

import dev.thangngo.travelmate.dtos.request.PlanMemberRequest;
import dev.thangngo.travelmate.dtos.response.ApiResponse;
import dev.thangngo.travelmate.dtos.response.PlanMemberResponse;
import dev.thangngo.travelmate.services.PlanMemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/plan-member")
public class PlanMemberController {

    private final PlanMemberService planMemberService;

    public PlanMemberController(PlanMemberService planMemberService) {
        this.planMemberService = planMemberService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<List<PlanMemberResponse>>> getAllPlanMembers(@PathVariable Long id) {
        List<PlanMemberResponse> list = planMemberService.getMembersByPlanId(id);
        return ResponseEntity.ok(ApiResponse.<List<PlanMemberResponse>>builder()
                .message("Success")
                .result(list)
                .build());
    }

    @DeleteMapping("/{planId}/members/{memberId}")
    public ResponseEntity<ApiResponse<Void>> deletePlanMember(
            @PathVariable Long planId,
            @PathVariable UUID memberId
    ) {
        planMemberService.removeMemberFromPlan(planId, memberId);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .message("Member removed successfully")
                .build());
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<PlanMemberResponse>> createPlanMember(
            @RequestBody PlanMemberRequest planMemberRequest
    ){
        PlanMemberResponse planMember = planMemberService.addMemberToPlan(planMemberRequest);
        return ResponseEntity.ok(ApiResponse.<PlanMemberResponse>builder()
                .message("Member added successfully")
                .result(planMember)
                .build());
    }

}
