package dev.thangngo.travelmate.mappers;

import dev.thangngo.travelmate.dtos.request.plan.PlanCreateRequest;
import dev.thangngo.travelmate.dtos.response.plan.ListPlanResponse;
import dev.thangngo.travelmate.dtos.response.plan.PlanResponse;
import dev.thangngo.travelmate.entities.Plan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface PlanMapper {
    PlanResponse toPlanResponse(Plan plan);

    Plan toPlan(PlanCreateRequest planCreateRequest);

    ListPlanResponse toListPlanResponse(Plan plans);

    @Mapping(source = "user", target = "owner")
    PlanResponse toPlanResponseWithOwner(Plan plan);
}

