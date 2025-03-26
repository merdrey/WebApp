package ru.bmstu.server.services;

import ru.bmstu.server.dto.SeatCardDTO;

import java.util.List;

public interface SeatCardService {

    SeatCardDTO create(SeatCardDTO dto);

    SeatCardDTO get(Integer id);

    List<SeatCardDTO> getAll();

    List<SeatCardDTO> getSeatsForVagon(Integer id);

    Boolean delete(Integer id);

    Boolean update(Integer id, SeatCardDTO dto);
}
