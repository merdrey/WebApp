package ru.bmstu.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.bmstu.server.entities.RouteStation;

import java.util.List;

public interface RouteStationRepo extends JpaRepository<RouteStation, Integer> {

    @Transactional
    @Query(value = "select rs from RouteStation rs where rs.route.id = ?1 order by rs.departureDate, rs.departureTime")
    List<RouteStation> getStationsForRoute(Integer id);
}
