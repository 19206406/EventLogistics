package com.desgroup.models.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "logistic")
@NoArgsConstructor
@AllArgsConstructor
public class Logistic extends Staff {
    @Column(nullable = false)
    private String zone;
    @Column(nullable = false)
    private Integer workingHours;
    @Column(nullable = false)
    private String role;
    @Column(nullable = true)
    private Integer score;
}
