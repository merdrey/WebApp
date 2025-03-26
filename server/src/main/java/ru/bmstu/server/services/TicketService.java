package ru.bmstu.server.services;

import ru.bmstu.server.dto.TicketDTO;

import java.util.List;

public interface TicketService {

    TicketDTO create(TicketDTO dto);

    TicketDTO get(Integer id);

    List<Object[]> getBoardState(Integer trId, Integer vId);

    boolean delete(Integer id);
}
