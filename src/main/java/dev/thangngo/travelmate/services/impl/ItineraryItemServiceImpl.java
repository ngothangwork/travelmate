package dev.thangngo.travelmate.services.impl;

import dev.thangngo.travelmate.dtos.request.itinerary.CreateItineraryItemRequest;
import dev.thangngo.travelmate.dtos.request.itinerary.UpdateItineraryItemRequest;
import dev.thangngo.travelmate.dtos.response.itinerary.ItineraryItemResponse;
import dev.thangngo.travelmate.entities.ItineraryItem;
import dev.thangngo.travelmate.exceptions.AppException;
import dev.thangngo.travelmate.exceptions.ErrorCode;
import dev.thangngo.travelmate.mappers.ItineraryItemMapper;
import dev.thangngo.travelmate.repositories.ItineraryItemRepository;
import dev.thangngo.travelmate.services.ItineraryItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;

@Service
@Transactional
public class ItineraryItemServiceImpl implements ItineraryItemService {

    private final ItineraryItemMapper itineraryItemMapper;
    private final ItineraryItemRepository itineraryItemRepository;

    public ItineraryItemServiceImpl(ItineraryItemMapper itineraryItemMapper,
                                    ItineraryItemRepository itineraryItemRepository) {
        this.itineraryItemMapper = itineraryItemMapper;
        this.itineraryItemRepository = itineraryItemRepository;
    }

    @Override
    @Cacheable(value = "itinerary_items", key = "#planId")
    public List<ItineraryItemResponse> getAllItineraryItemsByPlanId(Long planId) {
        List<ItineraryItem> itineraryItems = itineraryItemRepository.findByPlanId(planId);
        return itineraryItems.stream()
                .map(itineraryItemMapper::toItineraryItemResponse)
                .toList();
    }

    @Override
    @Cacheable(value = "itinerary_item", key = "#id")
    public ItineraryItemResponse getItineraryItemById(Long id) {
        ItineraryItem itineraryItem = itineraryItemRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ITINERARY_ITEM_NOT_FOUND));
        return itineraryItemMapper.toItineraryItemResponse(itineraryItem);
    }

    @Override
    @CacheEvict(value = {"itinerary_items", "itinerary_item"}, allEntries = true)
    public ItineraryItemResponse createItineraryItem(CreateItineraryItemRequest request) {
        ItineraryItem itineraryItem = itineraryItemMapper.toItineraryItem(request);
        // validate logic...
        itineraryItem = itineraryItemRepository.save(itineraryItem);
        return itineraryItemMapper.toItineraryItemResponse(itineraryItem);
    }

    @Override
    @CacheEvict(value = {"itinerary_items", "itinerary_item"}, allEntries = true)
    public ItineraryItemResponse updateItineraryItem(Long id, UpdateItineraryItemRequest request) {
        ItineraryItem itineraryItem = itineraryItemRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ITINERARY_ITEM_NOT_FOUND));
        itineraryItemMapper.updateItineraryItemFromRequest(request, itineraryItem);
        itineraryItem = itineraryItemRepository.save(itineraryItem);
        return itineraryItemMapper.toItineraryItemResponse(itineraryItem);
    }

    @Override
    @CacheEvict(value = {"itinerary_items", "itinerary_item"}, allEntries = true)
    public void deleteItineraryItem(Long id) {
        ItineraryItem itineraryItem = itineraryItemRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ITINERARY_ITEM_NOT_FOUND));
        itineraryItemRepository.delete(itineraryItem);
    }
}
