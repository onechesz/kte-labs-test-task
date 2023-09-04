package com.github.onechesz.ktelabstesttask.models;

import com.github.onechesz.ktelabstesttask.dtos.doctor.DoctorDTOO;
import jakarta.persistence.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

@Entity
@Table(name = "doctor")
public class DoctorModel {
    @Column(name = "id", unique = true, nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "uuid", unique = true, nullable = false)
    private String uuid;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @OneToMany(mappedBy = "doctorModel", cascade = CascadeType.ALL)
    private Set<TicketModel> ticketModelSet;

    public DoctorModel() {

    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull DoctorDTOO convertToDoctorDTOO(@NotNull DoctorModel doctorModel) {
        return new DoctorDTOO(doctorModel.id, doctorModel.fullName);
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

    public Set<TicketModel> getTicketModelSet() {
        return ticketModelSet;
    }

    public void setTicketModelSet(Set<TicketModel> ticketModelSet) {
        this.ticketModelSet = ticketModelSet;
    }
}
