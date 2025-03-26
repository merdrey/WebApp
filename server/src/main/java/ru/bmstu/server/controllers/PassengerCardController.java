package ru.bmstu.server.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.server.dto.PassengerCardDTO;
import ru.bmstu.server.services.impl.PassengerCardServiceImpl;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/passenger/")
public class PassengerCardController {
    private PassengerCardServiceImpl service;

    @CrossOrigin
    @PostMapping("/createPassenger")
    public ResponseEntity<PassengerCardDTO> create(@RequestBody PassengerCardDTO dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @CrossOrigin
    @GetMapping("/getPassenger/{id}")
    public ResponseEntity<PassengerCardDTO> get(@PathVariable(name = "id") Integer id) {
        return new ResponseEntity<>(service.get(id), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/getPassengersForUser/{id}")
    public ResponseEntity<List<Object[]>> getPassengersForUser(@PathVariable(name = "id") Integer id) {
        return new ResponseEntity<>(service.getPassengersForUser(id), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/getPassengerByUser/{id}")
    public ResponseEntity<List<PassengerCardDTO>> getPassengerByUser(@PathVariable(name = "id") Integer id) {
        return new ResponseEntity<>(service.getPassengersByUser(id), HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping("/deletePassenger/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable(name = "id") Integer id) {
        return service.delete(id) ? new ResponseEntity<>(true, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @CrossOrigin
    @PatchMapping("updatePassenger/{id}")
    public ResponseEntity<Boolean> update(@PathVariable(name = "id") Integer id, @RequestBody PassengerCardDTO dto) {
        return service.update(id, dto) ? new ResponseEntity<>(true, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
