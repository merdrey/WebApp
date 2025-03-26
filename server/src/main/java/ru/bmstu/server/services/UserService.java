package ru.bmstu.server.services;

import ru.bmstu.server.dto.UserDTO;

public interface UserService {
    UserDTO create(UserDTO dto);
    UserDTO getByLogin(String name);

    UserDTO getCurrentUser();
    Boolean checkPassword(Integer id, String password);
    Boolean update(Integer id, UserDTO dto);
    Boolean delete(Integer id);
}
