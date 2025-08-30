package dev.thangngo.travelmate.services.impl;

import dev.thangngo.travelmate.common.SlugUtil;
import dev.thangngo.travelmate.dtos.request.plan.PlanRequest;
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
    public PlanResponse createPlan(PlanRequest planRequest) {
        User user =  userRepository.getUserById(planRequest.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Plan plan = planMapper.toPlan(planRequest);
        plan.setCreatedAt(Instant.now());
        plan.setUser(user);
        plan.setSlug(SlugUtil.toSlug(planRequest.getName()));
        planRepository.save(plan);

        PlanMember planMember = new PlanMember();
        planMember.setUser(user);
        planMember.setPlan(plan);
        planMember.setRole(MemberRole.OWNER);
        planMemberRepository.save(planMember);

        return planMapper.toPlanResponseWithOwner(plan);
    }

    @Override
    public PlanResponse updatePlan(Long id, PlanRequest planRequest) {
        Optional<Plan> existingOpt = planRepository.findById(id);
        if (existingOpt.isEmpty()) {
            return null;
        }
        Plan existingPlant = existingOpt.get();
        existingPlant.setName(planRequest.getName());
        existingPlant.setSlug(SlugUtil.toSlug(planRequest.getName()));
        existingPlant.setDescription(planRequest.getDescription());
        existingPlant.setStartDate(planRequest.getStartDate());
        existingPlant.setEndDate(planRequest.getEndDate());
        existingPlant.setUpdatedAt(Instant.now());
        return planMapper.toPlanResponseWithOwner(planRepository.save(existingPlant));
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
