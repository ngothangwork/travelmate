package dev.thangngo.travelmate.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ApiResponse<T> implements Serializable {
    boolean success;
    private int code;
    private String message;
    private T result;

}
