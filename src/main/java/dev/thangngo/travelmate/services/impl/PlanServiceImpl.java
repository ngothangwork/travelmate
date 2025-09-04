package dev.thangngo.travelmate.services.impl;

import dev.thangngo.travelmate.common.SlugUtil;
import dev.thangngo.travelmate.dtos.request.plan.PlanCreateRequest;
import dev.thangngo.travelmate.dtos.request.plan.PlanUpdateRequest;
import dev.thangngo.travelmate.dtos.response.plan.ListPlanResponse;
import dev.thangngo.travelmate.dtos.response.plan.PlanResponse;
import dev.thangngo.travelmate.entities.Plan;
import dev.thangngo.travelmate.entities.PlanMember;
import dev.thangngo.travelmate.entities.User;
import dev.thangngo.travelmate.enums.MemberRole;
import dev.thangngo.travelmate.exceptions.AppException;
import dev.thangngo.travelmate.exceptions.ErrorCode;
import dev.thangngo.travelmate.mappers.PlanMapper;
import dev.thangngo.travelmate.repositories.PlanMemberRepository;
import dev.thangngo.travelmate.repositories.PlanRepository;
import dev.thangngo.travelmate.repositories.UserRepository;
import dev.thangngo.travelmate.services.PlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;
    private final PlanMapper planMapper;
    private final UserRepository userRepository;
    private final PlanMemberRepository planMemberRepository;

    public PlanServiceImpl(PlanRepository planRepository, PlanMapper planMapper, UserRepository userRepository, PlanMemberRepository planMemberRepository) {
        this.planRepository = planRepository;
        this.planMapper = planMapper;
        this.userRepository = userRepository;
        this.planMemberRepository = planMemberRepository;
    }

    @Override
    public PlanResponse getPlanByName(String name) {
        Plan plan = planRepository.getPlansByName(name);
        return plan == null ? null : planMapper.toPlanResponse(plan);
    }

    @Override
    public List<ListPlanResponse> getAllPlans() {
        List<Plan> plans = planRepository.findAll();
        return plans.stream().map(plan -> {
            ListPlanResponse response = planMapper.toListPlanResponse(plan);
            int memberCount = planMemberRepository.countByPlanId(plan.getId());
            response.setNumberOfMembers(memberCount);
            return response;
        }).toList();
    }

    @Override
    public PlanResponse findByIdAndSlug(Long id, String slug) {
        Plan plan = planRepository.findByIdAndSlug(id, slug)
                .orElseThrow(() -> new IllegalArgumentException("Plan not found"));
        return planMapper.toPlanResponseWithOwner(plan);
    }


    @Override
    public PlanResponse createPlan(PlanCreateRequest planCreateRequest) {
        User user =  userRepository.getUserById(planCreateRequest.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Plan plan = planMapper.toPlan(planCreateRequest);
        plan.setCreatedAt(Instant.now());
        plan.setUser(user);
        plan.setSlug(SlugUtil.toSlug(planCreateRequest.getName()));
        planRepository.save(plan);

        PlanMember planMember = new PlanMember();
        planMember.setUser(user);
        planMember.setPlan(plan);
        planMember.setRole(MemberRole.OWNER);
        planMemberRepository.save(planMember);

        return planMapper.toPlanResponseWithOwner(plan);
    }

    @Override
    public PlanResponse updatePlan(Long id, PlanUpdateRequest planRequest) {
        Optional<Plan> existingOpt = planRepository.findById(id);
        if (existingOpt.isEmpty()) {
            throw new AppException(ErrorCode.PLAN_NOT_FOUND);
        }

        Plan existingPlan = existingOpt.get();

        if (planRequest.getName() != null) {
            existingPlan.setName(planRequest.getName());
            existingPlan.setSlug(SlugUtil.toSlug(planRequest.getName()));
        }
        if (planRequest.getDescription() != null) {
            existingPlan.setDescription(planRequest.getDescription());
        }
        if (planRequest.getStartDate() != null) {
            existingPlan.setStartDate(planRequest.getStartDate());
        }
        if (planRequest.getEndDate() != null) {
            existingPlan.setEndDate(planRequest.getEndDate());
        }
        if (planRequest.getLocation() != null) {
            existingPlan.setLocation(planRequest.getLocation());
        }
        if (planRequest.getIsPublic() != null) {
            existingPlan.setIsPublic(planRequest.getIsPublic());
        }

        existingPlan.setUpdatedAt(Instant.now());

        Plan updated = planRepository.save(existingPlan);
        return planMapper.toPlanResponseWithOwner(updated);
    }



    @Override
    public void deletePlan(Long id) {
        Plan existingPlant = planRepository.findById(id).orElse(null);
        if (existingPlant == null) {
            log.error("Plan with id {} not found", id);
            throw new IllegalArgumentException("Plan with id " + id + " not found");
        }else{
            planRepository.delete(existingPlant);
        }
    }

    @Override
    public List<PlanResponse> getPlansByUserId(UUID userId) {
        return planRepository.getPlansByUserId(userId).stream().map(planMapper::toPlanResponse).toList();
    }

    @Override
    public Optional<PlanResponse> getPlanById(Long id) {
        return planRepository.findById(id)
                .map(planMapper::toPlanResponseWithOwner);
    }

}
