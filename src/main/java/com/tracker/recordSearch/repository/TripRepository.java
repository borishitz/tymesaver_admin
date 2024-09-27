package com.tracker.recordSearch.repository;


import com.tracker.recordSearch.domain.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    public List<Trip> findByTravelAgencyId(Long tripId);
    @Query("select p from Trip p where p.is_deleted = false and p.is_activated = true")
    List<Trip> getAllTrips();

    @Query("select p from Trip p where p.startLocation like %?1% or p.description like %?1%")
    List<Trip> findAllByNameOrDescription(String keyword);


    @Query("select p from Trip p inner join Category c ON c.id = p.category.id" +
            " where p.category.name = ?1 and p.is_activated = true and p.is_deleted = false")
    List<Trip> findAllByCategory(String category);

    @Query(value = "select " +
            "p.trip_id, p.startLocation, p.description, p.current_quantity, p.cost_price, p.category_id, p.sale_price, p.image, p.is_activated, p.is_deleted " +
            "from trips p where p.is_activated = true and p.is_deleted = false order by rand() limit 9", nativeQuery = true)
    List<Trip> randomTrip();

    @Query(value = "select " +
            "p.trip_id, p.startLocation, p.description, p.current_quantity, p.cost_price, p.category_id, p.sale_price, p.image, p.is_activated, p.is_deleted " +
            "from trips p where p.is_deleted = false and p.is_activated = true order by p.cost_price desc limit 9", nativeQuery = true)
    List<Trip> filterHighTrips();

    @Query(value = "select " +
            "p.trip_id, p.startLocation, p.description, p.current_quantity, p.cost_price, p.category_id, p.sale_price, p.image, p.is_activated, p.is_deleted " +
            "from trips p where p.is_deleted = false and p.is_activated = true order by p.cost_price asc limit 9", nativeQuery = true)
    List<Trip> filterLowerTrips();


    @Query(value = "select p.trip_id, p.startLocation, p.description, p.current_quantity, p.cost_price, p.category_id, p.sale_price, p.image, p.is_activated, p.is_deleted from trips p where p.is_deleted = false and p.is_activated = true limit 4", nativeQuery = true)
    List<Trip> listViewTrip();


    @Query(value = "select p from Trip p inner join Category c on c.id = ?1 and p.category.id = ?1 where p.is_activated = true and p.is_deleted = false")
    List<Trip> getTripByCategoryId(Long id);


    @Query("select p from Trip p where p.startLocation like %?1% or p.description like %?1%")
    List<Trip> searchTrips(String keyword);
}
