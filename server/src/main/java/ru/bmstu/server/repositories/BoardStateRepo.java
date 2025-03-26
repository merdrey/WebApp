package ru.bmstu.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bmstu.server.entities.BoardState;

public interface BoardStateRepo extends JpaRepository<BoardState, Integer> {
}
