package com.github.onechesz.ktelabstesttask.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "patient")
public class PatientModel {
    @Column(name = "id", unique = true, nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "uuid", unique = true, nullable = false)
    private String uuid;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @OneToMany(mappedBy = "patientModel", cascade = CascadeType.ALL)
    private Set<TicketModel> ticketModelSet;

    public PatientModel() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Set<TicketModel> getTicketModelSet() {
        return ticketModelSet;
    }

    public void setTicketModelSet(Set<TicketModel> ticketModelSet) {
        this.ticketModelSet = ticketModelSet;
    }
}
