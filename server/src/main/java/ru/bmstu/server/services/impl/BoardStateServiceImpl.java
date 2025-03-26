package ru.bmstu.server.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bmstu.server.dto.BoardStateDTO;
import ru.bmstu.server.entities.BoardState;
import ru.bmstu.server.repositories.BoardStateRepo;
import ru.bmstu.server.repositories.TicketRepo;
import ru.bmstu.server.services.BoardStateService;
import ru.bmstu.server.utils.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BoardStateServiceImpl implements BoardStateService {
    private BoardStateRepo repo;
    private TicketRepo ticketRepo;

    @Override
    public BoardStateDTO create(BoardStateDTO dto) {
        BoardState tmp = new BoardState();
        tmp.setTicket(ticketRepo.getReferenceById(dto.getTId()));
        tmp.setStatus(dto.getStatus());
        return Mapper.BSToBSDTO(repo.save(tmp));
    }

    @Override
    public List<BoardStateDTO> getAll() {
        return repo.findAll().stream().map(Mapper::BSToBSDTO).collect(Collectors.toList());
    }

    @Override
    public Boolean update(Integer id, BoardStateDTO dto) {
        if(repo.existsById(id)) {
            BoardState tmp = repo.getReferenceById(id);
            tmp.setTicket(ticketRepo.getReferenceById(dto.getTId()));
            tmp.setStatus(dto.getStatus());
            repo.save(tmp);
            return true;
        }
        return false;
    }
}
