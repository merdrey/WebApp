package ru.bmstu.server.services.impl;


import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.bmstu.server.dto.UserDTO;
import ru.bmstu.server.security.TokenResponse;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final UserServiceImpl userService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public TokenResponse signUp(UserDTO dto) {

        UserDTO user = userService.create(dto);

        String token = tokenService.generateToken(user);
        return new TokenResponse(token);
    }

    public TokenResponse signIn(UserDTO dto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                dto.getLogin(),
                dto.getPassword()
        ));

        String token = tokenService.generateToken(userService.getByLogin(dto.getLogin()));
        return new TokenResponse(token);
    }
}
