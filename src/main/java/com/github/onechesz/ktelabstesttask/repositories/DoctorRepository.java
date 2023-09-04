package com.github.onechesz.ktelabstesttask.repositories;

import com.github.onechesz.ktelabstesttask.models.DoctorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorModel, Integer> {

}
