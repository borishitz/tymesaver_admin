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
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private Long phoneNumber;

    private String messageDetail;

}
