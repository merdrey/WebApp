package ru.bmstu.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.bmstu.server.entities.Train;

import java.time.LocalDate;
import java.util.List;

public interface TrainRepo extends JpaRepository<Train, Integer> {
    @Transactional
    @Query(value = "select tr.id, tr.type, tr.route.rId, rs1.station.name, rs1.departureDate, rs1.departureTime," +
            "rs2.station.name, rs2.arriveDate, rs2.arriveTime, count(sc), min(sc.price) from Train tr" +
            " join RouteStation rs1 on tr.route.rId = rs1.route.rId " +
            "join RouteStation rs2 on tr.route.rId = rs2.route.rId " +
            "join Vagon v on tr.id = v.train.id " +
            "join SeatCard sc on v.id = sc.vagon.id" +
            " where rs1.station.id = ?1 and rs1.departureDate is not null and rs1.departureDate = ?2" +
            " and rs2.station.id = ?3 and rs2.arriveDate is not null" +
            " and sc.isBusy is false" +
            " group by tr.id, tr.type, tr.route.rId, rs1.station.name, rs1.departureDate, rs1.departureTime, rs2.station.name, rs2.arriveDate, rs2.arriveTime")
    List<Object[]> getSchedule(Integer depId, LocalDate depDate, Integer arrId);
}
