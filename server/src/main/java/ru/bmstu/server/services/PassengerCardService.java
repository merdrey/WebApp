package ru.bmstu.server.services;

import ru.bmstu.server.dto.PassengerCardDTO;
import ru.bmstu.server.entities.PassengerCard;

import java.util.List;

public interface PassengerCardService {

    PassengerCardDTO create(PassengerCardDTO dto);

    PassengerCardDTO get(Integer id);

    List<Object[]> getPassengersForUser(Integer id);

    List<PassengerCardDTO> getPassengersByUser(Integer id);

    Boolean delete(Integer id);

    Boolean update(Integer id, PassengerCardDTO dto);
}
