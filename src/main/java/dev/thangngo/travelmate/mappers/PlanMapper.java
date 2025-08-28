package dev.thangngo.travelmate.mappers;

import dev.thangngo.travelmate.dtos.request.PlanRequest;
import dev.thangngo.travelmate.dtos.response.PlanResponse;
import dev.thangngo.travelmate.entities.Plan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface PlanMapper {
    PlanResponse toPlanResponse(Plan plan);

    Plan toPlan(PlanRequest planRequest);

    @Mapping(source = "user", target = "owner")
    PlanResponse toPlanResponseWithOwner(Plan plan);
}

