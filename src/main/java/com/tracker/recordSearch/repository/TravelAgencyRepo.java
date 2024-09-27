package com.tracker.recordSearch.repository;

import com.tracker.recordSearch.domain.TravelAgency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelAgencyRepo extends JpaRepository<TravelAgency, Long> {

}
