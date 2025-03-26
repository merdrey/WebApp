package ru.bmstu.server.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import ru.bmstu.server.entities.Route;
@Getter
@Setter
@AllArgsConstructor
public class TrainDTO {
    @Nullable
    private Integer id;
    @NonNull
    private String type;
    @NonNull
    private Integer routeId;
}
