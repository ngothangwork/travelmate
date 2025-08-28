package dev.thangngo.travelmate.services.impl;

import dev.thangngo.travelmate.dtos.request.ItineraryItemRequest;
import dev.thangngo.travelmate.dtos.response.ItineraryItemResponse;
import dev.thangngo.travelmate.entities.ItineraryItem;
import dev.thangngo.travelmate.exceptions.AppException;
import dev.thangngo.travelmate.exceptions.ErrorCode;
import dev.thangngo.travelmate.mappers.ItineraryItemMapper;
import dev.thangngo.travelmate.repositories.ItineraryItemRepository;
import dev.thangngo.travelmate.services.ItineraryItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ItinernaryItemServiceImpl implements ItineraryItemService {

    private final ItineraryItemMapper itineraryItemMapper;
    private final ItineraryItemRepository itineraryItemRepository;

    public ItinernaryItemServiceImpl(ItineraryItemMapper itineraryItemMapper, ItineraryItemRepository itineraryItemRepository) {
        this.itineraryItemMapper = itineraryItemMapper;
        this.itineraryItemRepository = itineraryItemRepository;
    }

    @Override
    public List<ItineraryItemResponse> getAllItineraryItemsByPlanId(Long planId) {
        List<ItineraryItem> itineraryItems = itineraryItemRepository.findByPlanId(planId);
        return itineraryItems.stream()
                .map(itineraryItemMapper::toItineraryItemResponse)
                .collect(Collectors.toList());

    }

    @Override
    public ItineraryItemResponse getItineraryItemById(Long id) {
        ItineraryItem itineraryItem = itineraryItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Itinerary item not found"));
        return itineraryItemMapper.toItineraryItemResponse(itineraryItem);
    }

    @Override
    public ItineraryItemResponse createItineraryItem(ItineraryItemRequest itineraryItemRequest) {
        ItineraryItem itineraryItem = itineraryItemMapper.toItineraryItem(itineraryItemRequest);
        itineraryItem = itineraryItemRepository.save(itineraryItem);
        return itineraryItemMapper.toItineraryItemResponse(itineraryItem);
    }

    @Override
    public void deleteItineraryItem(Long id) {
        ItineraryItem itineraryItem = itineraryItemRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ITINERARY_ITEM_NOT_FOUND));

        itineraryItemRepository.delete(itineraryItem);
    }

    @Override
    public ItineraryItemResponse updateItineraryItem(Long id, ItineraryItemRequest itineraryItemRequest) {
        ItineraryItem itineraryItem = itineraryItemRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ITINERARY_ITEM_NOT_FOUND));

        itineraryItemMapper.updateItineraryItemFromRequest(itineraryItemRequest, itineraryItem);

        ItineraryItem updated = itineraryItemRepository.save(itineraryItem);

        return itineraryItemMapper.toItineraryItemResponse(updated);
    }
}
