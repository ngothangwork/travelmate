package dev.thangngo.travelmate.mappers;

import dev.thangngo.travelmate.dtos.request.itinerary.CreateItineraryItemRequest;
import dev.thangngo.travelmate.dtos.request.itinerary.UpdateItineraryItemRequest;
import dev.thangngo.travelmate.dtos.response.itinerary.ItineraryItemResponse;
import dev.thangngo.travelmate.entities.ItineraryItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ItineraryItemMapper {

    @Mapping(source = "plan.id", target = "planId")
    @Mapping(source = "createdBy.id", target = "createdById")
    ItineraryItemResponse toItineraryItemResponse(ItineraryItem itineraryItem);

    @Mapping(source = "planId", target = "plan.id")
    @Mapping(source = "createdBy", target = "createdBy.id")
    ItineraryItem toItineraryItem(CreateItineraryItemRequest request);

    void updateItineraryItemFromRequest(UpdateItineraryItemRequest request, @MappingTarget ItineraryItem entity);
}

