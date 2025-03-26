package ru.bmstu.server.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bmstu.server.dto.RouteStationDTO;
import ru.bmstu.server.entities.RouteStation;
import ru.bmstu.server.repositories.RouteRepo;
import ru.bmstu.server.repositories.RouteStationRepo;
import ru.bmstu.server.repositories.StationRepo;
import ru.bmstu.server.services.RouteStationService;
import ru.bmstu.server.utils.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RouteStationServiceImpl implements RouteStationService {

    private RouteStationRepo repo;
    private RouteRepo routeRepo;
    private StationRepo stationRepo;

    @Override
    public RouteStationDTO create(RouteStationDTO dto) {
        RouteStation station = new RouteStation();
        station.setId(dto.getId());
        station.setStation(stationRepo.getReferenceById(dto.getStationId()));
        station.setRoute(routeRepo.getReferenceById(dto.getRouteId()));
        station.setArriveTime(dto.getArriveTime());
        station.setArriveDate(dto.getArriveDate());
        station.setDepartureTime(dto.getDepartTime());
        station.setDepartureDate(dto.getDepartDate());
        return Mapper.RSToRSDTO(repo.save(station));
    }

    @Override
    public RouteStationDTO get(Integer id) {
        if(repo.existsById(id)) {
            return Mapper.RSToRSDTO(repo.getReferenceById(id));
        }
        return null;
    }

    @Override
    public List<RouteStationDTO> getAll() {
        return repo.findAll().stream().map(Mapper::RSToRSDTO).collect(Collectors.toList());
    }

    @Override
    public List<RouteStationDTO> getStationsForRoute(Integer id) {
        return repo.getStationsForRoute(id).stream().map(Mapper::RSToRSDTO).collect(Collectors.toList());
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
    public Boolean update(Integer id, RouteStationDTO dto) {
        if(repo.existsById(id)) {
            RouteStation tmp = repo.getReferenceById(id);
            tmp.setStation(stationRepo.getReferenceById(dto.getStationId()));
            tmp.setRoute(routeRepo.getReferenceById(dto.getRouteId()));
            tmp.setArriveDate(dto.getArriveDate());
            tmp.setArriveTime(dto.getArriveTime());
            tmp.setDepartureDate(dto.getDepartDate());
            tmp.setDepartureTime(dto.getDepartTime());
            repo.save(tmp);
            return true;
        }
        return false;
    }
}
