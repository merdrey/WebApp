package ru.bmstu.server.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import ru.bmstu.server.dto.UserDTO;
import ru.bmstu.server.entities.User;
import ru.bmstu.server.repositories.UserRepo;
import ru.bmstu.server.security.Role;
import ru.bmstu.server.services.UserService;
import ru.bmstu.server.utils.Mapper;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepo repo;

    @Override
    public UserDTO create(UserDTO dto) {
        if(!repo.existByLogin(dto.getLogin()) && !repo.existByEmail(dto.getEmail()) && !repo.existByPhone(dto.getPhone())) {
            User tmp = new User();
            tmp.setLogin(dto.getLogin());
            tmp.setPassword(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt()));
            tmp.setRole(Role.ROLE_USER);
            tmp.setPhone(dto.getPhone());
            tmp.setEmail(dto.getEmail());
            return Mapper.UserToUserDTO(repo.save(tmp));
        }
        return null;
    }

    @Override
    public UserDTO getByLogin(String name) {
        return Mapper.UserToUserDTO(repo.getUserByLogin(name));
    }

    public UserDetailsService userDetailsService() {
        return this::getByLogin;
    }

    @Override
    public UserDTO getCurrentUser() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByLogin(login);
    }

    @Override
    public Boolean checkPassword(Integer id, String password) {
        if(repo.existsById(id)) {
            User tmp = repo.getReferenceById(id);
            return BCrypt.checkpw(password, tmp.getPassword());
        }
        return false;
    }

    @Override
    public Boolean update(Integer id, UserDTO dto) {
        if(repo.existsById(id)) {
            User tmp = repo.getReferenceById(id);
            tmp.setLogin(dto.getLogin());
            tmp.setPassword(dto.getPassword());
            tmp.setRole(dto.getRole());
            tmp.setPhone(dto.getPhone());
            tmp.setEmail(dto.getEmail());
            repo.save(tmp);
            return true;
        }
        return false;
    }

    @Override
    public Boolean delete(Integer id) {
        if(repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }
}
