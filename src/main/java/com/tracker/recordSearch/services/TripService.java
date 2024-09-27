package com.tracker.recordSearch.services;

import com.tracker.recordSearch.domain.Trip;
import com.tracker.recordSearch.dto.TripDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TripService {
    List<Trip> findAll();

    List<TripDto> trips();

    List<TripDto> allTrips();

    public Trip saveTrip(Trip trip);

    Trip save(MultipartFile imageTrip, TripDto trip);

    public List<Trip> getTripByTravelAgencyId(Long id);

    Trip update(MultipartFile imageTrip, TripDto tripDto);

    void enableById(Long id);

    void deleteById(Long id);

    TripDto getById(Long id);

    Trip findById(Long id);

    List<TripDto> randomTrip();

    Page<TripDto> searchTrips(int pageNo, String keyword);

    Page<TripDto> getAllTrips(int pageNo);

    Page<TripDto> getAllTripsForCustomer(int pageNo);


    List<TripDto> findAllByCategory(String category);


    List<TripDto> filterHighTrips();

    List<TripDto> filterLowerTrips();

    List<TripDto> listViewTrips();

    List<TripDto> findByCategoryId(Long id);

    List<TripDto> searchTrips(String keyword);


}
