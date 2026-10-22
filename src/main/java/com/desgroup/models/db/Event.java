package com.desgroup.models.db;

import java.time.LocalDate;

import com.desgroup.models.Place;

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
@Table(name = "event")
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Place place;
    @Column(nullable = false)
    private String state;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private Integer startTime;
    @Column(nullable = false)
    private Integer endTime;
    @Column(nullable = false)
    private Integer maxCoordinators;
    @Column(nullable = false)
    private Integer maxLogistics;
    @Column(nullable = false)
    private Integer maxLogisticsPerCoordinator;

    public Event(Integer id, String name, Place place, String state, LocalDate date, Integer startTime) {
        this.id = id;
        this.name = name;
        this.place = place;
        this.state = state;
        this.date = date;
        this.startTime = startTime;
    }

    public Event(Integer maxLogistics, Integer maxLogisticsPerCoordinator, Integer maxCoordinators, Integer endTime,
            Integer startTime,
            LocalDate date, String state, Place place, String name, Integer id) {
        this.maxLogistics = maxLogistics;
        this.maxLogisticsPerCoordinator = maxLogisticsPerCoordinator;
        this.maxCoordinators = maxCoordinators;
        this.endTime = endTime;
        this.startTime = startTime;
        this.date = date;
        this.state = state;
        this.place = place;
        this.name = name;
        this.id = id;
    }
}
