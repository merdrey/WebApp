package ru.bmstu.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.bmstu.server.dto.RouteStationDTO;
import ru.bmstu.server.entities.Route;
import ru.bmstu.server.entities.RouteStation;

import java.util.List;

public interface RouteRepo extends JpaRepository<Route, Integer> {

}
