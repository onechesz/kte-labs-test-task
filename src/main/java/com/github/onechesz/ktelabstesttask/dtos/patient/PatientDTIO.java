package com.github.onechesz.ktelabstesttask.dtos.patient;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PatientDTIO {
    @NotNull(message = "Не может отсутствовать.")
    @Positive(message = "Должен быть положительным числом.")
    private int id;

    public PatientDTIO() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
