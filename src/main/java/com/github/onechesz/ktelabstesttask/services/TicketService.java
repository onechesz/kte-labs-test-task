package com.github.onechesz.ktelabstesttask.services;

import com.github.onechesz.ktelabstesttask.dtos.ticket.TicketDTOO;
import com.github.onechesz.ktelabstesttask.models.DoctorModel;
import com.github.onechesz.ktelabstesttask.models.PatientModel;
import com.github.onechesz.ktelabstesttask.models.TicketModel;
import com.github.onechesz.ktelabstesttask.repositories.DoctorRepository;
import com.github.onechesz.ktelabstesttask.repositories.PatientRepository;
import com.github.onechesz.ktelabstesttask.repositories.TicketRepository;
import com.github.onechesz.ktelabstesttask.utils.exceptions.ScheduleNotCreatedException;
import com.github.onechesz.ktelabstesttask.utils.exceptions.TicketNotTakenException;
import com.github.onechesz.ktelabstesttask.utils.exceptions.TicketsNotRequestedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TicketService {
    private final DoctorRepository doctorRepository;
    private final TicketRepository ticketRepository;
    private final PatientRepository patientRepository;

    public TicketService(DoctorRepository doctorRepository, TicketRepository ticketRepository, PatientRepository patientRepository) {
        this.doctorRepository = doctorRepository;
        this.ticketRepository = ticketRepository;
        this.patientRepository = patientRepository;
    }

    public void createDailySchedule(int doctorId, LocalDateTime scheduleStartTime, int duration, int count) {
        if (count > 0)
            if (LocalDateTime.now().isBefore(scheduleStartTime))
                doctorRepository.findById(doctorId).ifPresentOrElse(doctorModel -> {
                    LocalDate scheduleDate = scheduleStartTime.toLocalDate();

                    if (ticketRepository.findAllByStartTimeBetweenAndDoctorModel(scheduleDate.atStartOfDay(), scheduleDate.plusDays(1).atStartOfDay(), doctorModel).isEmpty()) {
                        List<TicketModel> ticketModelList = new ArrayList<>();
                        LocalDateTime ticketStartTime = scheduleStartTime;

                        for (int i = 0; i < count; i++) {
                            ticketModelList.add(new TicketModel(doctorModel, ticketStartTime));
                            ticketStartTime = ticketStartTime.plusMinutes(duration);
                        }

                        ticketRepository.saveAll(ticketModelList);
                    } else
                        throw new ScheduleNotCreatedException("Расписание на данную дату уже составлено.");
                }, () -> {
                    throw new ScheduleNotCreatedException("Врач с данным идентификатором не найден.");
                });
            else
                throw new ScheduleNotCreatedException("Нельзя создать расписание на прошедшую дату.");
        else
            throw new ScheduleNotCreatedException("Количество талонов должно быть положительным числом.");

    }

    @Transactional(readOnly = true)
    public List<TicketDTOO> findFreeToDoctorByDate(int doctorId, LocalDate date) {
        Optional<DoctorModel> doctorModelOptional = doctorRepository.findById(doctorId);

        if (doctorModelOptional.isPresent())
            return ticketRepository.findAllByStartTimeBetweenAndDoctorModelAndPatientModel(date.atStartOfDay(), date.plusDays(1).atStartOfDay(), doctorModelOptional.get(), null).stream().map(TicketModel::convertToTicketDTOO).toList();

        throw new TicketsNotRequestedException("Врач с данным идентификатором не найден.");
    }

    public void takeByIdAndPatientId(int id, int patientId) {
        ticketRepository.findById(id).ifPresentOrElse(ticketModel -> {
            if (ticketModel.getPatientModel() == null)
                patientRepository.findById(patientId).ifPresentOrElse(patientModel -> {
                    ticketModel.setPatientModel(patientModel);
                    ticketRepository.save(ticketModel);
                }, () -> {
                    throw new TicketNotTakenException("Пациент с данным идентификатором не найден.");
                });
            else
                throw new TicketNotTakenException("Нельзя занять занятый талон.");
        }, () -> {
            throw new TicketNotTakenException("Талон с данным идентификатором не найден.");
        });
    }

    @Transactional(readOnly = true)
    public List<TicketDTOO> findAllByPatientId(int patientId) {
        Optional<PatientModel> patientModelOptional = patientRepository.findById(patientId);

        if (patientModelOptional.isPresent())
            return ticketRepository.findAllByPatientModel(patientModelOptional.get()).stream().map(TicketModel::convertToTicketDTOO).toList();

        throw new TicketsNotRequestedException("Пациент с данным идентификатором не найден");
    }

    @Transactional(readOnly = true)
    public List<TicketDTOO> findAllByPatientUuid(String uuid) {
        Optional<PatientModel> patientModelOptional = patientRepository.findByUuid(uuid);

        if (patientModelOptional.isPresent())
            return ticketRepository.findAllByPatientModel(patientModelOptional.get()).stream().map(TicketModel::convertToTicketDTOO).toList();

        throw new TicketsNotRequestedException("Пациент с данным универсальным идентификатором не найден");
    }
}
