package com.github.onechesz.ktelabstesttask.repositories;

import com.github.onechesz.ktelabstesttask.models.DoctorModel;
import com.github.onechesz.ktelabstesttask.models.PatientModel;
import com.github.onechesz.ktelabstesttask.models.TicketModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<TicketModel, Integer> {
    List<TicketModel> findAllByStartTimeBetweenAndDoctorModel(LocalDateTime startTime, LocalDateTime endTime, DoctorModel doctorModel);

    List<TicketModel> findAllByStartTimeBetweenAndDoctorModelAndPatientModel(LocalDateTime startTime, LocalDateTime endTime, DoctorModel doctorModel, PatientModel patientModel);

    List<TicketModel> findAllByPatientModel(PatientModel patientModel);
}
