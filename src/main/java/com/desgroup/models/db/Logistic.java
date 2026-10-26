package com.desgroup.models.db;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Table(name = "logistic")
@DiscriminatorValue("LOGISTIC")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Logistic extends Staff {
    @Column(nullable = false)
    private String zone;

    @Column(name = "working_hours", nullable = false)
    private Integer workingHours = 0;

    @Column(nullable = false)
    private String role;

    @Column(nullable = true)
    private Integer score = 0;

    public Logistic(int idStaff, String name, String email, String phone, String position, String password,
            String zone, String role, int score, int workingHours) {
        super(idStaff, name, email, phone, position, password);
        this.zone = zone;
        this.role = role;
        this.score = score;
        this.workingHours = workingHours;
    }
}
