package ru.bmstu.server.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class PassengerCardDTO {
    @Nullable
    private Integer id;

    @NonNull
    private LocalDate birthdate;

    @NonNull
    private String data;

    @NonNull
    private String name;

    @NonNull
    private Integer uId;
}
