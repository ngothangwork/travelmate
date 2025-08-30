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
    public List<ItineraryItemResponse> getAllItineraryItemsByPlanId(Long planId) {
        List<ItineraryItem> itineraryItems = itineraryItemRepository.findByPlanId(planId);
        return itineraryItems.stream()
                .map(itineraryItemMapper::toItineraryItemResponse)
                .toList();
    }

    @Override
    public ItineraryItemResponse getItineraryItemById(Long id) {
        ItineraryItem itineraryItem = itineraryItemRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ITINERARY_ITEM_NOT_FOUND));
        return itineraryItemMapper.toItineraryItemResponse(itineraryItem);
    }

    @Override
    public ItineraryItemResponse createItineraryItem(CreateItineraryItemRequest request) {
        ItineraryItem itineraryItem = itineraryItemMapper.toItineraryItem(request);
        if(itineraryItem.getStartTime().isAfter(itineraryItem.getEndTime())){
            throw new AppException(ErrorCode.START_DATE_AFTER_END_DATE);
        }

        List<ItineraryItem> listItineraryItem = itineraryItemRepository.findByPlanIdAndDate(itineraryItem.getPlan().getId(), itineraryItem.getDate());
        for(ItineraryItem item : listItineraryItem){
            if(item.getEndTime().isBefore(itineraryItem.getStartTime()) || item.getStartTime().isAfter(itineraryItem.getEndTime())){

            }else {
                throw new AppException(ErrorCode.BUSY_IN_THIS_TIME);
            }
        }

        itineraryItem = itineraryItemRepository.save(itineraryItem);
        return itineraryItemMapper.toItineraryItemResponse(itineraryItem);
    }

    @Override
    public ItineraryItemResponse updateItineraryItem(Long id, UpdateItineraryItemRequest request) {
        ItineraryItem itineraryItem = itineraryItemRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ITINERARY_ITEM_NOT_FOUND));

        itineraryItemMapper.updateItineraryItemFromRequest(request, itineraryItem);
        itineraryItem = itineraryItemRepository.save(itineraryItem);

        return itineraryItemMapper.toItineraryItemResponse(itineraryItem);
    }

    @Override
    public void deleteItineraryItem(Long id) {
        ItineraryItem itineraryItem = itineraryItemRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ITINERARY_ITEM_NOT_FOUND));
        itineraryItemRepository.delete(itineraryItem);
    }
}
