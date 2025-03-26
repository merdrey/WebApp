package ru.bmstu.server.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bmstu.server.dto.VagonDTO;
import ru.bmstu.server.entities.Vagon;
import ru.bmstu.server.repositories.TrainRepo;
import ru.bmstu.server.repositories.VagonRepo;
import ru.bmstu.server.services.VagonService;
import ru.bmstu.server.utils.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VagonServiceImpl implements VagonService {

    private final VagonRepo repo;
    private final TrainRepo trainRepo;

    @Override
    public VagonDTO create(VagonDTO dto) {
        Vagon tmp = new Vagon();
        tmp.setTrain(trainRepo.getReferenceById(dto.getTrainNum()));
        tmp.setType(dto.getType());
        tmp.setNumber(dto.getNumber());
        return Mapper.VagonToVagonDTO(repo.save(tmp));
    }

    @Override
    public VagonDTO get(Integer id) {
        if(repo.existsById(id)) {
            return Mapper.VagonToVagonDTO(repo.getReferenceById(id));
        }
        return null;
    }

    @Override
    public List<VagonDTO> getAll() {
        return repo.findAll().stream().map(Mapper::VagonToVagonDTO).collect(Collectors.toList());
    }

    @Override
    public List<VagonDTO> getVagonsForTrain(Integer id) {
        return repo.getVagonsForTrain(id).stream().map(Mapper::VagonToVagonDTO).collect(Collectors.toList());
    }

    @Override
    public List<Object[]> getVagonsAndSeatsForTrain(Integer id) {
        return repo.getVagonsAndSeatsForTrain(id);
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
    public Boolean update(Integer id, VagonDTO dto) {
        if(repo.existsById(id)) {
            Vagon tmp = repo.getReferenceById(id);
            tmp.setTrain(trainRepo.getReferenceById(dto.getTrainNum()));
            tmp.setNumber(dto.getNumber());
            tmp.setType(dto.getType());
            repo.save(tmp);
            return true;
        }
        return false;
    }
}
