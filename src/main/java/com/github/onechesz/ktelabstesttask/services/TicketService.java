package com.github.onechesz.ktelabstesttask.services;

import com.github.onechesz.ktelabstesttask.models.TicketModel;
import com.github.onechesz.ktelabstesttask.repositories.DoctorRepository;
import com.github.onechesz.ktelabstesttask.repositories.TicketRepository;
import com.github.onechesz.ktelabstesttask.utils.exceptions.ScheduleNotCreatedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TicketService {
    private final DoctorRepository doctorRepository;
    private final TicketRepository ticketRepository;

    public TicketService(DoctorRepository doctorRepository, TicketRepository ticketRepository) {
        this.doctorRepository = doctorRepository;
        this.ticketRepository = ticketRepository;
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
}
