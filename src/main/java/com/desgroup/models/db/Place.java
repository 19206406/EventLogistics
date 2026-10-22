package com.desgroup.models.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "place")
@NoArgsConstructor
@AllArgsConstructor
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String country;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String placeName;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private Integer capacity;

    public Place(String country, String city, String placeName, String address, int capacity) {
        this.country = country;
        this.city = city;
        this.placeName = placeName;
        this.address = address;
        this.capacity = capacity;
    }
}
