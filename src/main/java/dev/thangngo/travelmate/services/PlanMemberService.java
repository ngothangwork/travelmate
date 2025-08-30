package dev.thangngo.travelmate.services;

import dev.thangngo.travelmate.dtos.request.planmember.PlanMemberRequest;
import dev.thangngo.travelmate.dtos.response.planmember.PlanMemberResponse;

import java.util.List;
import java.util.UUID;

public interface PlanMemberService {
    PlanMemberResponse addMemberToPlan(PlanMemberRequest planMemberRequest);
    void removeMemberFromPlan(Long planId, UUID memberId);
    PlanMemberResponse getMemberByPlanIdAndMemberId(Long planId, UUID memberId);
    List<PlanMemberResponse> getMembersByPlanId(Long planId);
}
