package ru.bmstu.server.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.server.dto.UserDTO;
import ru.bmstu.server.services.impl.UserServiceImpl;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private UserServiceImpl service;

    @CrossOrigin
    @PostMapping("/createUser")
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO dto) {
        UserDTO tmp = service.create(dto);
        return tmp != null ? new ResponseEntity<>(tmp, HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @CrossOrigin
    @GetMapping("/getUser/{login}")
    public ResponseEntity<UserDTO> getByLogin(@PathVariable(name = "login") String login) {
        return new ResponseEntity<>(service.getByLogin(login), HttpStatus.OK);
    }

    @CrossOrigin
    @PatchMapping("/updateUser/{id}")
    public ResponseEntity<Boolean> update(@PathVariable(name = "id") Integer id, @RequestBody UserDTO dto) {
        return service.update(id, dto) ? new ResponseEntity<>(true, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @CrossOrigin
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable(name = "id") Integer id) {
        return service.delete(id) ? new ResponseEntity<>(true, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
