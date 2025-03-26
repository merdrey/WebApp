package ru.bmstu.server.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class SeatCardDTO {
    @Nullable
    private Integer id;

    @NonNull
    private Integer vagonId;

    @NonNull
    private Boolean isBusy;

    @NonNull
    private Integer number;

    @NonNull
    private BigDecimal price;
}
