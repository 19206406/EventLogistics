package com.desgroup.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Table(name = "staff")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "staff_type", discriminatorType = DiscriminatorType.STRING)
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

    @Column(name = "staff_position", nullable = false)
    private String position;

    @Column(nullable = false)
    private String password;

    @Transient
    private Double salary;

    @OneToMany(mappedBy = "staff")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<EventAssignment> assignments = new ArrayList<>();

    @OneToMany(mappedBy = "assignedBy")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<EventAssignment> assignmentsMade = new ArrayList<>();

    public Staff(int idStaff, String name, String email, String phone, String position, String password) {
        this.id = idStaff;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.position = position;
        this.password = password;
    }

    public void addAssignment(EventAssignment assignment) {
        assignments.add(assignment);
        assignment.setStaff(this);
    }

    /**
     * Indica si este miembro del personal puede asignar a otro a un evento.
     * Por defecto nadie puede asignar; cada subclase habilita los casos permitidos.
     */
    public boolean canAssign(Staff target) {
        return false;
    }
}
