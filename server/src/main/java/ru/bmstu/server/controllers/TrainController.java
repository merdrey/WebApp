package ru.bmstu.server.controllers;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.server.dto.TrainDTO;
import ru.bmstu.server.services.impl.TrainServiceImpl;

import java.time.LocalDate;

@RestController
@AllArgsConstructor
@RequestMapping("/train")
public class TrainController {
    private final TrainServiceImpl service;

    @CrossOrigin
    @PostMapping("/createTrain")
    public ResponseEntity<?> createTrain(@RequestBody TrainDTO dto) {
        return new ResponseEntity<>(service.createTrain(dto), HttpStatus.CREATED);
    }

    @CrossOrigin
    @GetMapping("/getTrain/{id}")
    public ResponseEntity<?> getTrainById(@PathVariable(name = "id") Integer id) {
        return service.getTrain(id) != null
                ? new ResponseEntity<>(service.getTrain(id), HttpStatus.FOUND)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @CrossOrigin
    @GetMapping("/getAllTrains")
    public ResponseEntity<?> getAllTrains() {
        return service.getAllTrains() != null ? new ResponseEntity<>(service.getAllTrains(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @CrossOrigin
    @GetMapping("/getTrainsBySt/{dep}/{arr}/{date}")
    public ResponseEntity<?> getTrains(@PathVariable(name = "dep") Integer dep, @PathVariable(name = "arr") Integer arr, @PathVariable(name = "date") LocalDate date) {
        return new ResponseEntity<>(service.getTrainsBySt(dep, date, arr), HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping("/deleteTrain/{id}")
    public ResponseEntity<?> deleteTrainById(@PathVariable(name = "id") Integer id) {
        return service.deleteTrain(id) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @CrossOrigin
    @PatchMapping("/updateTrain/{id}")
    public ResponseEntity<?> updateTrainById(@PathVariable(name = "id") Integer id, @RequestBody TrainDTO dto) {
        return service.updateTrain(id, dto) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
