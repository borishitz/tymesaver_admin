package com.tracker.recordSearch.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "trips", uniqueConstraints = @UniqueConstraint(columnNames = {"image"}))
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private Long id;
    private String startLocation;
    private String destination;

    private String busNumber;
    private String departureTime;

    private String description;
    private int currentQuantity;

    private double costPrice;
    private double salePrice;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;

    @ManyToOne
    private TravelAgency travelAgency;

    private boolean is_activated;
    private boolean is_deleted;

}
