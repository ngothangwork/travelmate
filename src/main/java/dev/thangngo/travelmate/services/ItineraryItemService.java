package dev.thangngo.travelmate.services;

import dev.thangngo.travelmate.dtos.request.itinerary.CreateItineraryItemRequest;
import dev.thangngo.travelmate.dtos.request.itinerary.UpdateItineraryItemRequest;
import dev.thangngo.travelmate.dtos.response.itinerary.ItineraryItemResponse;

import java.util.List;

public interface ItineraryItemService {
    List<ItineraryItemResponse> getAllItineraryItemsByPlanId(Long planId);
    ItineraryItemResponse getItineraryItemById(Long id);
    ItineraryItemResponse createItineraryItem(CreateItineraryItemRequest request);
    ItineraryItemResponse updateItineraryItem(Long id, UpdateItineraryItemRequest request);
    void deleteItineraryItem(Long id);
}
