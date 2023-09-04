package com.github.onechesz.ktelabstesttask.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket")
public class TicketModel {
    @Column(name = "id", unique = true, nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private DoctorModel doctorModel;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientModel patientModel;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    public TicketModel() {

    }

    public TicketModel(DoctorModel doctorModel, LocalDateTime startTime) {
        this.doctorModel = doctorModel;
        this.startTime = startTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DoctorModel getDoctorModel() {
        return doctorModel;
    }

    public void setDoctorModel(DoctorModel doctorModel) {
        this.doctorModel = doctorModel;
    }

    public PatientModel getPatientModel() {
        return patientModel;
    }

    public void setPatientModel(PatientModel patientModel) {
        this.patientModel = patientModel;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
}
