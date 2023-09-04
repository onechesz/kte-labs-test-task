package com.github.onechesz.ktelabstesttask.controllers;

import com.github.onechesz.ktelabstesttask.dtos.patient.PatientDTIO;
import com.github.onechesz.ktelabstesttask.dtos.ticket.TicketDTOO;
import com.github.onechesz.ktelabstesttask.services.TicketService;
import com.github.onechesz.ktelabstesttask.utils.exceptions.ExceptionResponse;
import com.github.onechesz.ktelabstesttask.utils.exceptions.TicketNotTakenException;
import com.github.onechesz.ktelabstesttask.utils.exceptions.TicketsNotRequestedException;
import jakarta.validation.Valid;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping(path = "/api/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping(path = "/{doctorId}/{date}")
    @ResponseBody
    public List<TicketDTOO> requestFreeToDoctorByDate(@PathVariable(name = "doctorId") int doctorId, @PathVariable(name = "date") String date) {
        try {
            return ticketService.findFreeToDoctorByDate(doctorId, LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        } catch (DateTimeParseException dateTimeParseException) {
            throw new TicketsNotRequestedException("Неверный формат даты.");
        }
    }

    @Contract("_ -> new")
    @ExceptionHandler(value = TicketsNotRequestedException.class)
    private @NotNull ResponseEntity<ExceptionResponse> ticketsNotRequestedExceptionHandler(@NotNull TicketsNotRequestedException ticketsNotRequestedException) {
        return new ResponseEntity<>(new ExceptionResponse(ticketsNotRequestedException.getMessage(), System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> takeById(@PathVariable(name = "id") int id, @RequestBody @Valid @NotNull PatientDTIO patientDTIO, BindingResult bindingResult) {
        checkForTicketNotTakenExceptionsAndThrow(bindingResult);

        ticketService.takeByIdAndPatientId(id, patientDTIO.getId());

        return ResponseEntity.ok(HttpStatus.OK);
    }

    private void checkForTicketNotTakenExceptionsAndThrow(@NotNull BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessagesBuilder = new StringBuilder();

            for (FieldError fieldError : bindingResult.getFieldErrors())
                errorMessagesBuilder.append(fieldError.getField()).append(" — ").append(fieldError.getDefaultMessage()).append(", ");

            errorMessagesBuilder.setLength(errorMessagesBuilder.length() - 2);

            throw new TicketsNotRequestedException(errorMessagesBuilder.toString());
        }
    }

    @GetMapping(path = "/{patientId}")
    @ResponseBody
    public List<TicketDTOO> requestAllByPatient(@PathVariable(name = "patientId") @NotNull String patientId) {
        if (patientId.length() < 36)
            try {
                return ticketService.findAllByPatientId(Integer.parseInt(patientId));
            } catch (NumberFormatException numberFormatException) {
                throw new TicketsNotRequestedException("Неверный формат данного идентификатора пациента.");
            }

        return ticketService.findAllByPatientUuid(patientId);
    }

    @Contract("_ -> new")
    @ExceptionHandler(value = TicketNotTakenException.class)
    private @NotNull ResponseEntity<ExceptionResponse> ticketNotTakenExceptionHandler(@NotNull TicketNotTakenException ticketNotTakenException) {
        return new ResponseEntity<>(new ExceptionResponse(ticketNotTakenException.getMessage(), System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }
}
