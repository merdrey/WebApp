package ru.bmstu.server.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bmstu.server.dto.SeatCardDTO;
import ru.bmstu.server.entities.SeatCard;
import ru.bmstu.server.repositories.SeatCardRepo;
import ru.bmstu.server.repositories.VagonRepo;
import ru.bmstu.server.services.SeatCardService;
import ru.bmstu.server.utils.Mapper;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SeatCardServiceImpl implements SeatCardService {

    private SeatCardRepo repo;
    private VagonRepo vagonRepo;

    @Override
    public SeatCardDTO create(SeatCardDTO dto) {
        SeatCard card = new SeatCard();
        card.setNumber(dto.getNumber());
        card.setVagon(vagonRepo.getReferenceById(dto.getVagonId()));
        card.setIsBusy(dto.getIsBusy());
        card.setPrice(dto.getPrice());
        return Mapper.SCToSCDTO(repo.save(card));
    }

    @Override
    public SeatCardDTO get(Integer id) {
        if(repo.existsById(id)) {
            return Mapper.SCToSCDTO(repo.getReferenceById(id));
        }
        return null;
    }

    @Override
    public List<SeatCardDTO> getAll() {
        return repo.findAll().stream().map(Mapper::SCToSCDTO).sorted(Comparator.comparing(SeatCardDTO::getNumber)).collect(Collectors.toList());
    }

    @Override
    public List<SeatCardDTO> getSeatsForVagon(Integer id) {
        return repo.getSeatsForVagon(id).stream().map(Mapper::SCToSCDTO).collect(Collectors.toList());
    }

    @Override
    public Boolean delete(Integer id) {
        if(repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Boolean update(Integer id, SeatCardDTO dto) {
        if(repo.existsById(id)) {
            SeatCard tmp = repo.getReferenceById(id);
            tmp.setVagon(vagonRepo.getReferenceById(dto.getVagonId()));
            tmp.setNumber(dto.getNumber());
            tmp.setIsBusy(dto.getIsBusy());
            tmp.setPrice(dto.getPrice());
            repo.save(tmp);
            return true;
        }
        return false;
    }
}
