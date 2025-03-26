package ru.bmstu.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.bmstu.server.entities.Vagon;

import java.util.List;
import java.util.Objects;

public interface VagonRepo extends JpaRepository<Vagon, Integer> {
    @Transactional
    @Query(value="select v from Vagon v where v.train.id = ?1 order by v.number")
    List<Vagon> getVagonsForTrain(Integer id);

    @Transactional
    @Query(value = "select v.id, v.number, v.type, count(sc), min(sc.price) from Vagon v" +
            " join SeatCard sc on v.id = sc.vagon.id" +
            " where sc.isBusy is false" +
            " and v.train.id = ?1" +
            " group by v.id, v.number, v.type" +
            " order by v.number")
    List<Object[]> getVagonsAndSeatsForTrain(Integer id);
}
