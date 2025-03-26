package ru.bmstu.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bmstu.server.entities.TicketJournal;

public interface TicketJournalRepo extends JpaRepository<TicketJournal, Integer> {

}
