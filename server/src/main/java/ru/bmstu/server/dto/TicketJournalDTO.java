package ru.bmstu.server.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
public class TicketJournalDTO {
    @Nullable
    private Integer id;

    @NonNull
    private Integer ticketId;

    @NonNull
    private LocalDate localDate;

    @NonNull
    private LocalTime localTime;
}
