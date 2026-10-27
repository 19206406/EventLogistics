package com.desgroup.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "place_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Place place;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private LocalDate date;

    @Column(name = "start_time", nullable = false)
    private Integer startTime = 0;

    @Column(name = "end_time", nullable = false)
    private Integer endTime = 0;

    @Column(name = "max_coordinators", nullable = false)
    private Integer maxCoordinators = 0;

    @Column(name = "max_logistics", nullable = false)
    private Integer maxLogistics = 0;

    @Column(name = "max_logistics_per_coordinator", nullable = false)
    private Integer maxLogisticsPerCoordinator = 0;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<EventAssignment> assignments = new ArrayList<>();

    public Event(Integer id, String name, Place place, String state, LocalDate date, Integer startTime) {
        this.name = name;
        this.place = place;
        this.state = state;
        this.date = date;
        this.startTime = startTime;
    }

    public Event(Integer maxLogistics, Integer maxLogisticsPerCoordinator, Integer maxCoordinators, Integer endTime,
            Integer startTime, LocalDate date, String state, Place place, String name) {
        this.maxLogistics = maxLogistics;
        this.maxLogisticsPerCoordinator = maxLogisticsPerCoordinator;
        this.maxCoordinators = maxCoordinators;
        this.endTime = endTime;
        this.startTime = startTime;
        this.date = date;
        this.state = state;
        this.place = place;
        this.name = name;
    }

    public void addAssignment(EventAssignment assignment) {
        assignments.add(assignment);
        assignment.setEvent(this);
    }

    public void removeAssignment(EventAssignment assignment) {
        assignments.remove(assignment);
        assignment.setEvent(null);
    }
}
