package ru.bmstu.server.services;

import ru.bmstu.server.dto.VagonDTO;

import java.util.List;

public interface VagonService {

    VagonDTO create(VagonDTO dto);

    VagonDTO get(Integer id);

    List<VagonDTO> getAll();

    List<VagonDTO> getVagonsForTrain(Integer id);

    List<Object[]> getVagonsAndSeatsForTrain(Integer id);

    Boolean delete(Integer id);

    Boolean update(Integer id, VagonDTO dto);
}
