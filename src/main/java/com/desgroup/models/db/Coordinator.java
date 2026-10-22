package com.desgroup.models.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "coordinator")
@NoArgsConstructor
@AllArgsConstructor
public class Coordinator extends Staff {
    @Column(nullable = false)
    private String company;
}
