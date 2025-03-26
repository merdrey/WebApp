package ru.bmstu.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.bmstu.server.entities.Ticket;

import java.util.List;

public interface TicketRepo extends JpaRepository<Ticket, Integer> {
    @Transactional
    @Query(value = "select t.seat.number, t.passenger.name, t.passenger.data, t.passenger.user.phone  from Ticket t" +
            " where t.seat.vagon.train.id = ?1 and t.seat.vagon.id = ?2")
    List<Object[]> getBoardState(Integer trId, Integer vId);
}
