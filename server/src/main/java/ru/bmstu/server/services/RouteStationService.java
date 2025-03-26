package ru.bmstu.server.services;

import ru.bmstu.server.dto.RouteStationDTO;

import java.util.List;

public interface RouteStationService {

    RouteStationDTO create(RouteStationDTO dto);

    RouteStationDTO get(Integer id);

    List<RouteStationDTO> getAll();

    List<RouteStationDTO> getStationsForRoute(Integer id);

    Boolean delete(Integer id);

    Boolean update(Integer id, RouteStationDTO dto);
}
