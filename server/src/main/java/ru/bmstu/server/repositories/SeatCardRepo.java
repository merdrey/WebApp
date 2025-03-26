package ru.bmstu.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.bmstu.server.entities.SeatCard;

import java.util.List;

public interface SeatCardRepo extends JpaRepository<SeatCard, Integer> {
    @Transactional
    @Query(value = "select sc from SeatCard sc where sc.vagon.id = ?1 order by sc.number")
    List<SeatCard> getSeatsForVagon(Integer id);
}
