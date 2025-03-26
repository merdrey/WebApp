package ru.bmstu.server.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VagonDTO {

    @Nullable
    private Integer id;

    @NonNull
    private Integer trainNum;

    @NonNull
    private Integer number;

    @NonNull
    private String type;
}
