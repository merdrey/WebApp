package ru.bmstu.server.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Getter
public class TicketDTO {
    @Nullable
    private Integer id;

    @NonNull
    private Integer passengerId;

    @NonNull
    private Integer seatId;

    @NonNull
    private Integer depStation;

    @NonNull
    private Integer arrStation;
}
