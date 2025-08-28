package dev.thangngo.travelmate.services;

import dev.thangngo.travelmate.dtos.request.ItineraryItemRequest;
import dev.thangngo.travelmate.dtos.response.ItineraryItemResponse;

import java.util.List;

public interface ItineraryItemService {
    List<ItineraryItemResponse> getAllItineraryItemsByPlanId(Long planId);
    ItineraryItemResponse getItineraryItemById(Long id);
    ItineraryItemResponse createItineraryItem(ItineraryItemRequest itineraryItemRequest);
    void deleteItineraryItem(Long id);
    ItineraryItemResponse updateItineraryItem(Long id, ItineraryItemRequest itineraryItemRequest);
}
