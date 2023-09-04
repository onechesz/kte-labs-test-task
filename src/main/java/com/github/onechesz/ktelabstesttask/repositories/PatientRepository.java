package com.github.onechesz.ktelabstesttask.repositories;

import com.github.onechesz.ktelabstesttask.models.PatientModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<PatientModel, Integer> {
    Optional<PatientModel> findByUuid(String uuid);
}
