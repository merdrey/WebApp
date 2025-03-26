package ru.bmstu.server.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bmstu.server.dto.PassengerCardDTO;
import ru.bmstu.server.entities.PassengerCard;
import ru.bmstu.server.repositories.PassengerCardRepo;
import ru.bmstu.server.repositories.UserRepo;
import ru.bmstu.server.services.PassengerCardService;
import ru.bmstu.server.utils.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PassengerCardServiceImpl implements PassengerCardService {

    private final PassengerCardRepo repo;
    private final UserRepo userRepo;

    @Override
    public PassengerCardDTO create(PassengerCardDTO dto) {
        PassengerCard card = new PassengerCard();
        card.setBirthdate(dto.getBirthdate());
        card.setData(dto.getData());
        card.setName(dto.getName());
        card.setUser(userRepo.getReferenceById(dto.getUId()));
        return Mapper.PCToPCDTO(repo.save(card));
    }

    @Override
    public PassengerCardDTO get(Integer id) {
        if(repo.existsById(id)) {
            return Mapper.PCToPCDTO(repo.getReferenceById(id));
        }
        return null;
    }

    @Override
    public List<Object[]> getPassengersForUser(Integer id) {
        return repo.getPassengersForUser(id);
    }

    @Override
    public List<PassengerCardDTO> getPassengersByUser(Integer id) {
        return repo.getPassengersByUser(id).stream().map(Mapper::PCToPCDTO).collect(Collectors.toList());
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
    public Boolean update(Integer id, PassengerCardDTO dto) {
        if(repo.existsById(id)) {
            PassengerCard card = repo.getReferenceById(id);
            card.setBirthdate(dto.getBirthdate());
            card.setData(dto.getData());
            card.setName(dto.getName());
            repo.save(card);
            return true;
        }
        return false;
    }
}
