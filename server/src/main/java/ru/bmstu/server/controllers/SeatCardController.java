package ru.bmstu.server.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.server.dto.SeatCardDTO;
import ru.bmstu.server.services.impl.SeatCardServiceImpl;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/seatCard")
public class SeatCardController {

    private SeatCardServiceImpl service;

    @CrossOrigin
    @PostMapping("/createSeatCard")
    public ResponseEntity<SeatCardDTO> create(@RequestBody SeatCardDTO dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @CrossOrigin
    @GetMapping("/getSeatCard/{id}")
    public ResponseEntity<SeatCardDTO> get(@PathVariable(name = "id") Integer id) {
        return new ResponseEntity<>(service.get(id), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/getAllSeatCards")
    public ResponseEntity<List<SeatCardDTO>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/getSeatsForVagon/{id}")
    public ResponseEntity<List<SeatCardDTO>> getSeatsForVagon(@PathVariable(name = "id") Integer id) {
        return new ResponseEntity<>(service.getSeatsForVagon(id), HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping("/deleteSeatCard/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable(name = "id") Integer id) {
        return service.delete(id) ? new ResponseEntity<>(true, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @CrossOrigin
    @PatchMapping("/updateSeatCard/{id}")
    public ResponseEntity<Boolean> update(@PathVariable(name = "id") Integer id, @RequestBody SeatCardDTO dto) {
        return service.update(id, dto) ? new ResponseEntity<>(true, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
