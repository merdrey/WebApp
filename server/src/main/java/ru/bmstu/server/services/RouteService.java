package ru.bmstu.server.services;

import ru.bmstu.server.dto.RouteDTO;
import ru.bmstu.server.dto.RouteStationDTO;

import java.util.List;

public interface RouteService {
    RouteDTO create();

    List<RouteDTO> getAll();

    Boolean delete(Integer id);
}
