package ru.bmstu.server.services;

import ru.bmstu.server.dto.StationDTO;

import java.util.List;

public interface StationService {
    StationDTO createStation(StationDTO dto);

    StationDTO getStation(Integer id);

    List<StationDTO> getAllStations();

    boolean deleteStation(Integer id);

    boolean updateStation(Integer id, StationDTO dto);
}
