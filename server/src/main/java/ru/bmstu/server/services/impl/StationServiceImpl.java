package ru.bmstu.server.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.bmstu.server.dto.StationDTO;
import ru.bmstu.server.entities.Station;
import ru.bmstu.server.repositories.StationRepo;
import ru.bmstu.server.services.StationService;
import ru.bmstu.server.utils.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StationServiceImpl implements StationService {

    private final StationRepo repo;

    @Override
    public StationDTO createStation(StationDTO dto) {
        Station tmp = new Station();
        tmp.setName(dto.getNameSt());
        return Mapper.StationToStationDTO(repo.save(tmp));
    }

    @Override
    public StationDTO getStation(Integer id) {
        if(repo.existsById(id)) {
            return Mapper.StationToStationDTO(repo.getReferenceById(id));
        }
        return null;
    }

    @Override
    public List<StationDTO> getAllStations() {
        return repo.findAll(Sort.by(Sort.Direction.ASC, "name")).stream().map(Mapper::StationToStationDTO).collect(Collectors.toList());
    }

    @Override
    public boolean deleteStation(Integer id) {
        if(repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateStation(Integer id, StationDTO dto) {
        if(repo.existsById(id)) {
            Station tmp = repo.getReferenceById(id);
            tmp.setName(dto.getNameSt());
            repo.save(tmp);
            return true;
        }
        return false;
    }
}
