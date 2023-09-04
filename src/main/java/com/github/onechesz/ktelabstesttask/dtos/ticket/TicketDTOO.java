package com.github.onechesz.ktelabstesttask.dtos.ticket;

import com.github.onechesz.ktelabstesttask.dtos.doctor.DoctorDTOO;

import java.time.LocalDateTime;

public class TicketDTOO {
    private int id;

    private DoctorDTOO doctorDTOO;

    private LocalDateTime startTime;

    public TicketDTOO() {

    }

    public TicketDTOO(int id, DoctorDTOO doctorDTOO, LocalDateTime startTime) {
        this.id = id;
        this.doctorDTOO = doctorDTOO;
        this.startTime = startTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DoctorDTOO getDoctorDTOO() {
        return doctorDTOO;
    }

    public void setDoctorDTOO(DoctorDTOO doctorDTOO) {
        this.doctorDTOO = doctorDTOO;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
}
