package dev.thangngo.travelmate.mappers;

import dev.thangngo.travelmate.dtos.request.ItineraryItemRequest;
import dev.thangngo.travelmate.dtos.response.ItineraryItemResponse;
import dev.thangngo.travelmate.entities.ItineraryItem;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ItineraryItemMapper {
    ItineraryItemResponse toItineraryItemResponse(ItineraryItem itineraryItem);
    ItineraryItem toItineraryItem(ItineraryItemRequest itineraryItemRequest);
    void updateItineraryItemFromRequest(ItineraryItemRequest request, @MappingTarget ItineraryItem entity);
}
