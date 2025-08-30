package dev.thangngo.travelmate.mappers;

import dev.thangngo.travelmate.dtos.response.user.UserResponse;
import dev.thangngo.travelmate.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User user);

    User toUser(UserResponse userResponse);
}
