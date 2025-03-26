package ru.bmstu.server.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bmstu.server.dto.TicketDTO;
import ru.bmstu.server.entities.Ticket;
import ru.bmstu.server.repositories.PassengerCardRepo;
import ru.bmstu.server.repositories.SeatCardRepo;
import ru.bmstu.server.repositories.TicketRepo;
import ru.bmstu.server.services.TicketService;
import ru.bmstu.server.utils.Mapper;

import java.util.List;

@Service
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepo repo;
    private final PassengerCardRepo passengerRepo;
    private final SeatCardRepo seatRepo;

    @Override
    public TicketDTO create(TicketDTO dto) {
        Ticket ticket = new Ticket();
        ticket.setSeat(seatRepo.getReferenceById(dto.getSeatId()));
        ticket.setPassenger(passengerRepo.getReferenceById(dto.getPassengerId()));
        ticket.setDepStation(dto.getDepStation());
        ticket.setArrStation(dto.getArrStation());
        return Mapper.TicketToTicketDTO(repo.save(ticket));
    }

    @Override
    public TicketDTO get(Integer id) {
        if(repo.existsById(id)) {
            return Mapper.TicketToTicketDTO(repo.getReferenceById(id));
        }
        return null;
    }

    @Override
    public List<Object[]> getBoardState(Integer trId, Integer vId) {
        return repo.getBoardState(trId, vId);
    }

    @Override
    public boolean delete(Integer id) {
        if(repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }
}
