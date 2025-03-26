package ru.bmstu.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.bmstu.server.entities.User;

public interface UserRepo extends JpaRepository<User, Integer> {
    @Transactional
    @Query(value = "select u from User u where u.login = ?1")
    User getUserByLogin(String login);

    @Transactional
    @Query(value = "select exists (select 1 from User u where u.login = ?1)")
    Boolean existByLogin(String login);
    @Transactional
    @Query(value = "select exists (select 1 from User u where u.email = ?1)")
    Boolean existByEmail(String email);
    @Transactional
    @Query(value = "select exists (select 1 from User u where u.phone = ?1)")
    Boolean existByPhone(String phone);
}
