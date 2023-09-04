package com.github.onechesz.ktelabstesttask.endpoints;

import com.github.onechesz.ktelabstesttask.services.TicketService;
import com.github.onechesz.ktelabstesttask.utils.exceptions.ScheduleNotCreatedException;
import org.jetbrains.annotations.NotNull;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ws.ktelabstesttask.schedule.CreateScheduleRequest;
import ws.ktelabstesttask.schedule.CreateScheduleResponse;

@Endpoint
public class ScheduleEndpoint {
    private static final String NAMESPACE_URI = "http://ktelabstesttask.ws/schedule";

    private final TicketService ticketService;

    public ScheduleEndpoint(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createScheduleRequest")
    @ResponsePayload
    public CreateScheduleResponse createScheduleResponse(@RequestPayload @NotNull CreateScheduleRequest createScheduleRequest) {
        CreateScheduleResponse createScheduleResponse = new CreateScheduleResponse();

        try {
            ticketService.createDailySchedule(createScheduleRequest.getDoctorId(), createScheduleRequest.getScheduleStartTime().toGregorianCalendar().toZonedDateTime().toLocalDateTime(), createScheduleRequest.getDuration(), createScheduleRequest.getCount());
        } catch (ScheduleNotCreatedException scheduleNotCreatedException) {
            createScheduleResponse.setStatus(scheduleNotCreatedException.getMessage());

            return createScheduleResponse;
        }

        createScheduleResponse.setStatus("Успешно.");

        return createScheduleResponse;
    }
}
