package com.desgroup.models;

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
@Table(name = "coordinator")
@DiscriminatorValue("COORDINATOR")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Coordinator extends Staff {
    @Column(nullable = false)
    private String company;

    public Coordinator(String name, String email, String phone, String position, String password,
            String company) {
        super(name, email, phone, position, password);
        this.company = company;
    }

    @Override
    public boolean canAssign(Staff target) {
        return target instanceof Logistic;
    }
}
