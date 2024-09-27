package com.tracker.recordSearch.impl;

import com.tracker.recordSearch.domain.Trip;
import com.tracker.recordSearch.dto.TripDto;
import com.tracker.recordSearch.repository.TripRepository;
import com.tracker.recordSearch.services.TripService;
import com.tracker.recordSearch.utils.ImageUpload;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {
    private final TripRepository tripRepository;

    private final ImageUpload imageUpload;

    @Override
    public List<Trip> findAll() {
        return tripRepository.findAll();
    }


    public Trip saveTrip(Trip trip){
        return tripRepository.save(trip);
    }

    @Override
    public List<TripDto> trips() {
        return transferData(tripRepository.getAllTrips());
    }

    @Override
    public List<Trip> getTripByTravelAgencyId(Long id) {
        return tripRepository.findByTravelAgencyId(id);
    }

    @Override
    public List<TripDto> allTrips() {
        List<Trip> trips = tripRepository.findAll();
        List<TripDto> tripDtos = transferData(trips);
        return tripDtos;
    }

    @Override
    public Trip save(MultipartFile imageTrip, TripDto tripDto) {
        Trip trip = new Trip();
        try {
            if (imageTrip == null) {
                trip.setImage(null);
            } else {
                imageUpload.uploadFile(imageTrip);
                trip.setImage(Base64.getEncoder().encodeToString(imageTrip.getBytes()));
            }
//            trip.setName(tripDto.getName());
            trip.setDescription(tripDto.getDescription());
            trip.setCurrentQuantity(tripDto.getCurrentQuantity());
            trip.setCostPrice(tripDto.getCostPrice());
            trip.setCategory(tripDto.getCategory());
            trip.set_deleted(false);
            trip.set_activated(true);
            return tripRepository.save(trip);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Trip update(MultipartFile imageTrip, TripDto tripDto) {
        try {
            Trip tripUpdate = tripRepository.getReferenceById(tripDto.getId());
            if (imageTrip.getBytes().length > 0) {
                if (imageUpload.checkExist(imageTrip)) {
                    tripUpdate.setImage(tripUpdate.getImage());
                } else {
                    imageUpload.uploadFile(imageTrip);
                    tripUpdate.setImage(Base64.getEncoder().encodeToString(imageTrip.getBytes()));
                }
            }
            tripUpdate.setCategory(tripDto.getCategory());
            tripUpdate.setId(tripUpdate.getId());
//            tripUpdate.setName(tripDto.getName());
            tripUpdate.setDescription(tripDto.getDescription());
            tripUpdate.setCostPrice(tripDto.getCostPrice());
            tripUpdate.setSalePrice(tripDto.getSalePrice());
            tripUpdate.setCurrentQuantity(tripDto.getCurrentQuantity());
            return tripRepository.save(tripUpdate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void enableById(Long id) {
        Trip trip = tripRepository.getById(id);
        trip.set_activated(true);
        trip.set_deleted(false);
        tripRepository.save(trip);
    }

    @Override
    public void deleteById(Long id) {
        Trip trip =tripRepository.getById(id);
        trip.set_deleted(true);
        trip.set_activated(false);
        tripRepository.save(trip);
    }

    @Override
    public TripDto getById(Long id) {
        TripDto tripDto = new TripDto();
        Trip trip = tripRepository.getById(id);
        tripDto.setId(trip.getId());
//        tripDto.setName(trip.getName());
        tripDto.setDescription(trip.getDescription());
        tripDto.setCostPrice(trip.getCostPrice());
        tripDto.setSalePrice(trip.getSalePrice());
        tripDto.setCurrentQuantity(trip.getCurrentQuantity());
        tripDto.setCategory(trip.getCategory());
        tripDto.setImage(trip.getImage());
        return tripDto;
    }

    @Override
    public Trip findById(Long id) {
        return tripRepository.findById(id).get();
    }

    @Override
    public List<TripDto> randomTrip() {
        return transferData(tripRepository.randomTrip());
    }

    @Override
    public Page<TripDto> searchTrips(int pageNo, String keyword) {
        List<Trip> trips = tripRepository.findAllByNameOrDescription(keyword);
        List<TripDto> tripDtoList = transferData(trips);
        Pageable pageable = PageRequest.of(pageNo, 5);
        Page<TripDto> dtoPage = toPage(tripDtoList, pageable);
        return dtoPage;
    }

    @Override
    public Page<TripDto> getAllTrips(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 6);
        List<TripDto> tripDtoLists = this.allTrips();
        Page<TripDto> tripDtoPage = toPage(tripDtoLists, pageable);
        return tripDtoPage;
    }

    @Override
    public Page<TripDto> getAllTripsForCustomer(int pageNo) {
        return null;
    }

    @Override
    public List<TripDto> findAllByCategory(String category) {
        return transferData(tripRepository.findAllByCategory(category));
    }

    @Override
    public List<TripDto> filterHighTrips() {
        return transferData(tripRepository.filterHighTrips());
    }

    @Override
    public List<TripDto> filterLowerTrips() {
        return transferData(tripRepository.filterLowerTrips());
    }

    @Override
    public List<TripDto> listViewTrips() {
        return transferData(tripRepository.listViewTrip());
    }

    @Override
    public List<TripDto> findByCategoryId(Long id) {
        return transferData(tripRepository.getTripByCategoryId(id));
    }

    @Override
    public List<TripDto> searchTrips(String keyword) {
        return transferData(tripRepository.searchTrips(keyword));
    }

    private Page toPage(List list, Pageable pageable) {
        if (pageable.getOffset() >= list.size()) {
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = ((pageable.getOffset() + pageable.getPageSize()) > list.size())
                ? list.size()
                : (int) (pageable.getOffset() + pageable.getPageSize());
        List subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList, pageable, list.size());
    }

    private List<TripDto> transferData(List<Trip> trips) {
        List<TripDto> tripDtos = new ArrayList<>();
        for (Trip trip : trips) {
            TripDto tripDto = new TripDto();
            tripDto.setId(trip.getId());
//            tripDto.setName(trip.getName());
            tripDto.setCurrentQuantity(trip.getCurrentQuantity());
            tripDto.setCostPrice(trip.getCostPrice());
            tripDto.setSalePrice(trip.getSalePrice());
            tripDto.setDescription(trip.getDescription());
            tripDto.setImage(trip.getImage());
            tripDto.setCategory(trip.getCategory());
            tripDto.setActivated(trip.is_activated());
            tripDto.setDeleted(trip.is_deleted());
            tripDtos.add(tripDto);
        }
        return tripDtos;
    }
}
