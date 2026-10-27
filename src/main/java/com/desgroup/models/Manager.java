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
@Table(name = "manager")
@DiscriminatorValue("MANAGER")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Manager extends Staff {
    @Column(nullable = false)
    private String company;

    public Manager(int idStaff, String name, String email, String phone, String position, String password,
            String company) {
        super(idStaff, name, email, phone, position, password);
        this.company = company;
    }

    @Override
    public boolean canAssign(Staff target) {
        return target instanceof Coordinator;
    }
}
