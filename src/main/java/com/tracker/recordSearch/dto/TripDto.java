package com.tracker.recordSearch.dto;

import com.tracker.recordSearch.domain.Category;
import com.tracker.recordSearch.domain.TravelAgency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripDto {

    private Long id;
    private String startLocation;

    private String destination;
    private String busNumber;

    private String departureTime;
    private String description;

    private int currentQuantity;
    private double costPrice;

    private double salePrice;
    private String image;

    private TravelAgency travelAgency;
    private Category category;

    private boolean activated;
    private boolean deleted;

    private String currentPage;
}
