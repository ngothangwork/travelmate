package dev.thangngo.travelmate.controllers;

import dev.thangngo.travelmate.dtos.request.itinerary.CreateItineraryItemRequest;
import dev.thangngo.travelmate.dtos.request.itinerary.UpdateItineraryItemRequest;
import dev.thangngo.travelmate.dtos.response.ApiResponse;
import dev.thangngo.travelmate.dtos.response.itinerary.ItineraryItemResponse;
import dev.thangngo.travelmate.services.ItineraryItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itinerary-item")
public class ItineraryItemController {

    private final ItineraryItemService itineraryItemService;

    public ItineraryItemController(ItineraryItemService itineraryItemService) {
        this.itineraryItemService = itineraryItemService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ItineraryItemResponse>>> getAllItineraryItemsByPlanId(
            @RequestParam Long planId) {
        List<ItineraryItemResponse> list = itineraryItemService.getAllItineraryItemsByPlanId(planId);
        return ResponseEntity.ok(ApiResponse.<List<ItineraryItemResponse>>builder()
                .success(true)
                .message("get itinerary items by plan id success")
                .result(list)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ItineraryItemResponse>> getItineraryItemById(@PathVariable Long id) {
        ItineraryItemResponse itineraryItem = itineraryItemService.getItineraryItemById(id);
        return ResponseEntity.ok(ApiResponse.<ItineraryItemResponse>builder()
                .success(true)
                .message("get itinerary item by id success")
                .result(itineraryItem)
                .build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ItineraryItemResponse>> createItineraryItem(
            @RequestBody CreateItineraryItemRequest request) {
        ItineraryItemResponse response = itineraryItemService.createItineraryItem(request);
        return ResponseEntity.ok(ApiResponse.<ItineraryItemResponse>builder()
                .success(true)
                .message("create itinerary item success")
                .result(response)
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ItineraryItemResponse>> updateItineraryItem(
            @PathVariable Long id,
            @RequestBody UpdateItineraryItemRequest request) {
        ItineraryItemResponse response = itineraryItemService.updateItineraryItem(id, request);
        return ResponseEntity.ok(ApiResponse.<ItineraryItemResponse>builder()
                .success(true)
                .message("update itinerary item success")
                .result(response)
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteItineraryItem(@PathVariable Long id) {
        itineraryItemService.deleteItineraryItem(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .success(true)
                .message("delete itinerary item success")
                .build());
    }
}
