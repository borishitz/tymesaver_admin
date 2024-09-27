package com.tracker.recordSearch.repository;

import com.tracker.recordSearch.domain.MemberShip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberShipRepository extends JpaRepository<MemberShip, Long> {

}
