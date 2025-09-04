package dev.thangngo.travelmate.services.impl;

import dev.thangngo.travelmate.dtos.request.planmember.PlanMemberCreateRequest;
import dev.thangngo.travelmate.dtos.response.planmember.PlanMemberResponse;
import dev.thangngo.travelmate.entities.Plan;
import dev.thangngo.travelmate.entities.PlanMember;
import dev.thangngo.travelmate.entities.User;
import dev.thangngo.travelmate.enums.MemberRole;
import dev.thangngo.travelmate.exceptions.AppException;
import dev.thangngo.travelmate.exceptions.ErrorCode;
import dev.thangngo.travelmate.mappers.PlanMemberMapper;
import dev.thangngo.travelmate.repositories.PlanMemberRepository;
import dev.thangngo.travelmate.repositories.PlanRepository;
import dev.thangngo.travelmate.repositories.UserRepository;
import dev.thangngo.travelmate.services.PlanMemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PlanMemberServiceImpl implements PlanMemberService {

    private final PlanMemberRepository planMemberRepository;
    private final UserRepository userRepository;
    private final PlanRepository planRepository;

    private final PlanMemberMapper planMemberMapper;

    public PlanMemberServiceImpl(PlanMemberRepository planMemberRepository, UserRepository userRepository, PlanRepository planRepository, PlanMemberMapper planMemberMapper) {
        this.planMemberRepository = planMemberRepository;
        this.userRepository = userRepository;
        this.planRepository = planRepository;
        this.planMemberMapper = planMemberMapper;
    }

    @Override
    public PlanMemberResponse addMemberToPlan(PlanMemberCreateRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Plan plan = planRepository.findById(request.getPlanId())
                .orElseThrow(() -> new AppException(ErrorCode.PLAN_NOT_FOUND));

        boolean exists = planMemberRepository.existsByUserAndPlan(user, plan);
        if (exists) {
            throw new AppException(ErrorCode.MEMBER_ALREADY_EXISTS);
        }

        PlanMember planMember = new PlanMember();
        planMember.setUser(user);
        planMember.setPlan(plan);
        planMember.setRole(MemberRole.VIEWER);

        planMember = planMemberRepository.save(planMember);



        return planMemberMapper.toResponse(planMember);
    }



    @Override
    public void removeMemberFromPlan(Long planId, UUID memberId) {
        PlanMember planMember = planMemberRepository.findByUserIdAndPlanId(memberId, planId)
                .orElseThrow(() -> new AppException(ErrorCode.PLAN_MEMBER_NOT_FOUND));

        planMemberRepository.delete(planMember);
    }


    @Override
    public PlanMemberResponse getMemberByPlanIdAndMemberId(Long planId, UUID memberId) {
        return planMemberRepository.findByUserIdAndPlanId(memberId, planId)
                .map(planMemberMapper::toResponse)
                .orElseThrow(() -> new AppException(ErrorCode.PLAN_MEMBER_NOT_FOUND));
    }

    @Override
    public List<PlanMemberResponse> getMembersByPlanId(Long planId) {
        List<PlanMember> members = planMemberRepository.findAllByPlanId(planId);
        return members.stream()
                .map(planMemberMapper::toResponse)
                .toList();
    }

}
