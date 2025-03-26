package ru.bmstu.server.services;

import ru.bmstu.server.dto.TicketJournalDTO;

import java.util.List;

public interface TicketJournalService {
    TicketJournalDTO create(TicketJournalDTO dto);

    TicketJournalDTO get(Integer id);

    List<TicketJournalDTO> getAll();

    Boolean delete(Integer id);
}
