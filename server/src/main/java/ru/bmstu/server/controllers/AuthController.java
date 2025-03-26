package ru.bmstu.server.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.server.dto.UserDTO;
import ru.bmstu.server.security.TokenResponse;
import ru.bmstu.server.services.impl.AuthenticationService;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private AuthenticationService service;

    @CrossOrigin
    @PostMapping("/signUp")
    public TokenResponse signUp(@RequestBody UserDTO dto) {
        return service.signUp(dto);
    }

    @CrossOrigin
    @PostMapping("/signIn")
    public TokenResponse signIn(@RequestBody UserDTO dto) {
        return service.signIn(dto);
    }
}
