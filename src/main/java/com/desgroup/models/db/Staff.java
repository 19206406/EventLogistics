package com.desgroup.models.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "staff")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@AllArgsConstructor
public abstract class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = true)
    private String phone;
    @Column(nullable = false)
    private String position;
    @Column(nullable = false, unique = true)
    private String password;
    @Transient
    private Double salary;

    public Staff(int idStaff, String name, String email, String phone, String position, String password) {
        this.id = idStaff;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.position = position;
        this.password = password;
    }
}
