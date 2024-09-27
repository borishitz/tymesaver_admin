package com.tracker.recordSearch.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberShip {
    @Id

    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;

    private String method;

    private String amount;

    private String currency;

    private Long description;
}
