package ru.bmstu.server.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
public class RouteStationDTO {
    @Nullable
    private Integer id;

    @NonNull
    private Integer routeId;

    @NonNull
    private Integer stationId;

    @Nullable
    private LocalDate arriveDate;

    @Nullable
    private LocalTime arriveTime;

    @Nullable
    private LocalDate departDate;

    @Nullable
    private LocalTime departTime;
}
