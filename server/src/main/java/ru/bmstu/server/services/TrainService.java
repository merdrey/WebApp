package ru.bmstu.server.services;

import ru.bmstu.server.dto.TrainDTO;

import java.time.LocalDate;
import java.util.List;

public interface TrainService {
    TrainDTO createTrain(TrainDTO trDTO);
    TrainDTO getTrain(Integer id);
    List<TrainDTO> getAllTrains();
    List <Object[]> getTrainsBySt(Integer depId, LocalDate depDate, Integer arrId);
    boolean deleteTrain(Integer id);
    boolean updateTrain(Integer id, TrainDTO dto);
}
