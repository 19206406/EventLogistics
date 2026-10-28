package com.desgroup.models;

import com.desgroup.utils.StaffRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Table(name = "event_assignment", uniqueConstraints = @UniqueConstraint(name = "uk_event_assignment_staff_event", columnNames = {
        "staff_id", "event_id" }))
@NoArgsConstructor
@AllArgsConstructor
public class EventAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "staff_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Staff staff;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "assigned_by_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Staff assignedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "staff_role", nullable = false)
    private StaffRole staffRole;

    public EventAssignment(Staff staff, Event event, Staff assignedBy, StaffRole staffRole) {
        this.staff = staff;
        this.event = event;
        this.assignedBy = assignedBy;
        this.staffRole = staffRole;
    }
}
