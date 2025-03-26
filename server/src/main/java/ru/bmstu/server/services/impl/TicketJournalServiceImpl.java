package ru.bmstu.server.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bmstu.server.dto.TicketJournalDTO;
import ru.bmstu.server.entities.TicketJournal;
import ru.bmstu.server.repositories.TicketJournalRepo;
import ru.bmstu.server.repositories.TicketRepo;
import ru.bmstu.server.services.TicketJournalService;
import ru.bmstu.server.utils.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TicketJournalServiceImpl implements TicketJournalService {
    private TicketJournalRepo repo;
    private TicketRepo ticketRepo;

    @Override
    public TicketJournalDTO create(TicketJournalDTO dto) {
        TicketJournal journal = new TicketJournal();
        journal.setTicket(ticketRepo.getReferenceById(dto.getTicketId()));
        journal.setOrderDate(dto.getLocalDate());
        journal.setOrderTime(dto.getLocalTime());
        return Mapper.TJToTJDTO(repo.save(journal));
    }

    @Override
    public TicketJournalDTO get(Integer id) {
        if(repo.existsById(id)) {
            return Mapper.TJToTJDTO(repo.getReferenceById(id));
        }
        return null;
    }

    @Override
    public List<TicketJournalDTO> getAll() {
        return repo.findAll().stream().map(Mapper::TJToTJDTO).collect(Collectors.toList());
    }

    @Override
    public Boolean delete(Integer id) {
        if(repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }
}
