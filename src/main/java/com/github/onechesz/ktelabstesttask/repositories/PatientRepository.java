package com.github.onechesz.ktelabstesttask.repositories;

import com.github.onechesz.ktelabstesttask.models.PatientModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<PatientModel, Integer> {

}
