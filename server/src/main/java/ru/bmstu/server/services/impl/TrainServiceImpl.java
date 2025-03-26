package ru.bmstu.server.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bmstu.server.dto.TrainDTO;
import ru.bmstu.server.entities.Train;
import ru.bmstu.server.repositories.RouteRepo;
import ru.bmstu.server.repositories.TrainRepo;
import ru.bmstu.server.services.TrainService;
import ru.bmstu.server.utils.Mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TrainServiceImpl implements TrainService {

    private final TrainRepo repo;
    private final RouteRepo routeRepo;

    @Override
    public TrainDTO createTrain(TrainDTO trDTO) {
        Train train = new Train();
        train.setRoute(routeRepo.getReferenceById(trDTO.getRouteId()));
        train.setType(trDTO.getType());
        return Mapper.TrainToTrainDTO(repo.save(train));
    }

    @Override
    public TrainDTO getTrain(Integer id) {
        if(repo.existsById(id)) {
            return Mapper.TrainToTrainDTO(repo.getReferenceById(id));
        }
        return null;
    }

    @Override
    public List<TrainDTO> getAllTrains() {
        return repo.findAll().stream().map(Mapper::TrainToTrainDTO).collect(Collectors.toList());
    }

    @Override
    public List<Object[]> getTrainsBySt(Integer depId, LocalDate depDate, Integer arrId) {
        return repo.getSchedule(depId, depDate, arrId);
    }
    @Override
    public boolean deleteTrain(Integer id) {
        if(repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateTrain(Integer id, TrainDTO dto) {
        if(repo.existsById(id)) {
            Train temp = repo.getReferenceById(id);
            temp.setType(dto.getType());
            temp.setRoute(routeRepo.getReferenceById(dto.getRouteId()));
            repo.save(temp);
            return true;
        }
        return false;
    }
}
