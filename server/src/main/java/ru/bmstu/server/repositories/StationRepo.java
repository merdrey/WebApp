package ru.bmstu.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bmstu.server.entities.Station;

public interface StationRepo extends JpaRepository<Station, Integer> {
}
