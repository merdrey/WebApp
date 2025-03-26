package ru.bmstu.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.bmstu.server.entities.PassengerCard;

import java.util.List;

public interface PassengerCardRepo extends JpaRepository<PassengerCard, Integer> {
    @Transactional
    @Query(value = "select pc.id, t.id, sc.id, pc.name, pc.birthdate, sc.number, v.number, tr.id," +
            " r.rId, rs1.station.name, rs1.departureDate, rs1.departureTime," +
            " rs2.station.name, rs2.arriveDate, rs2.arriveTime" +
            " from PassengerCard pc" +
            " join Ticket t on pc.id = t.passenger.id" +
            " join SeatCard sc on sc.id = t.seat.id" +
            " join Vagon v on v.id = sc.vagon.id" +
            " join Train tr on tr.id = v.train.id" +
            " join Route r on r.rId = tr.route.rId" +
            " join RouteStation rs1 on rs1.route.rId = r.rId and rs1.station.id = t.depStation" +
            " join RouteStation rs2 on rs2.route.rId = r.rId and rs2.station.id = t.arrStation" +
            " where pc.user.id = ?1")
    List<Object[]> getPassengersForUser(Integer id);

    @Transactional
    @Query(value = "select pc from PassengerCard pc" +
            " where pc.user.id = ?1")
    List<PassengerCard> getPassengersByUser(Integer id);
}
