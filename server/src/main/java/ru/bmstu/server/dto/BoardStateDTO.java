package ru.bmstu.server.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BoardStateDTO {
    @Nullable
    private Integer id;
    @NonNull
    private Integer tId;
    @NonNull
    private Boolean status;
}
