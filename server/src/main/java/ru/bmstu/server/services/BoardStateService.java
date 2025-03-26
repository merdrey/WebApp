package ru.bmstu.server.services;

import ru.bmstu.server.dto.BoardStateDTO;

import java.util.List;

public interface BoardStateService {
    BoardStateDTO create(BoardStateDTO dto);
    List<BoardStateDTO> getAll();
    Boolean update(Integer id, BoardStateDTO dto);
}
