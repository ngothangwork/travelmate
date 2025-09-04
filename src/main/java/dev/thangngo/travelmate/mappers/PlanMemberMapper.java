package dev.thangngo.travelmate.mappers;

import dev.thangngo.travelmate.dtos.response.planmember.PlanMemberResponse;
import dev.thangngo.travelmate.entities.PlanMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface PlanMemberMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.name", target = "name")
    @Mapping(source = "user.avatar", target = "avatar")
    PlanMemberResponse toResponse(PlanMember planMember);

}
