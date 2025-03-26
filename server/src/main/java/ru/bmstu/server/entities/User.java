package ru.bmstu.server.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.bmstu.server.security.Role;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_u")
    private Integer id;

    @Column(name = "login_u")
    private String login;

    @Column(name = "password_u")
    private String password;

    @Column(name = "role_u")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "phone_u")
    private String phone;

    @Column(name = "email_u")
    private String email;
}
