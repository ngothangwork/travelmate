package dev.thangngo.travelmate.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    APP_EXCEPTION(1001, "Application exception"),
    VALIDATION_FAILED(1002, "Validation failed"),
    CONSTRAINT_VIOLATION(1003, "Constraint violation"),
    ILLEGAL_ARGUMENT(1004, "Invalid argument"),
    NULL_POINTER(1005, "Null pointer exception"),
    ILLEGAL_STATE(1006, "Illegal state"),
    RUNTIME_ERROR(1000, "Runtime error"),
    UNEXPECTED_ERROR(9999, "Unexpected error"),
    MEMBER_ALREADY_EXISTS(1007, "User is already a member of this plan"),
    USER_NOT_FOUND(1008, "User not found"),
    PLAN_NOT_FOUND(1009, "Plan not found"),
    PLAN_MEMBER_NOT_FOUND(1010, "Plan member not found"),
    ITINERARY_ITEM_NOT_FOUND(1011, "Itinerary item not found"),
    START_DATE_AFTER_END_DATE(1012, "Start date must be before end date"),
    BUSY_IN_THIS_TIME(1013, "You are busy in this time");

    private final int code;
    private final String message;
}
