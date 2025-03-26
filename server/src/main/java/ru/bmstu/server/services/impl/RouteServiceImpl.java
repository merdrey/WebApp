package ru.bmstu.server.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bmstu.server.dto.RouteDTO;
import ru.bmstu.server.dto.RouteStationDTO;
import ru.bmstu.server.entities.Route;
import ru.bmstu.server.repositories.RouteRepo;
import ru.bmstu.server.services.RouteService;
import ru.bmstu.server.utils.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RouteServiceImpl implements RouteService {
    private RouteRepo repo;

    @Override
    public RouteDTO create() {
        return Mapper.RouteToRouteDTO(repo.save(new Route()));
    }

    @Override
    public List<RouteDTO> getAll() {
        return repo.findAll().stream().map(Mapper::RouteToRouteDTO).collect(Collectors.toList());
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
