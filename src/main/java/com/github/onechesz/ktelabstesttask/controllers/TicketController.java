package com.github.onechesz.ktelabstesttask.controllers;

import com.github.onechesz.ktelabstesttask.dtos.ticket.TicketDTOO;
import com.github.onechesz.ktelabstesttask.services.TicketService;
import com.github.onechesz.ktelabstesttask.utils.exceptions.ExceptionResponse;
import com.github.onechesz.ktelabstesttask.utils.exceptions.TicketsNotRequestedException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
