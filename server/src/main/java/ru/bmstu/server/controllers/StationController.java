package ru.bmstu.server.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.server.dto.StationDTO;
import ru.bmstu.server.entities.Station;
import ru.bmstu.server.services.impl.StationServiceImpl;

import java.util.List;

@RestController
@AllArgsConstructor
public class StationController {

    private StationServiceImpl service;

    @CrossOrigin
    @PostMapping("/createStation")
    public ResponseEntity<StationDTO> createStation(@RequestBody StationDTO dto) {
        return new ResponseEntity<>(service.createStation(dto), HttpStatus.CREATED);
    }

    @GetMapping("/getStation/{id}")
    public ResponseEntity<StationDTO> getStationById(@PathVariable(name = "id") Integer id) {
        return new ResponseEntity<>(service.getStation(id), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/getAllStations")
    public ResponseEntity<List<StationDTO>> getAllStations() {
        return new ResponseEntity<>(service.getAllStations(), HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping("/deleteStation/{id}")
    public ResponseEntity<Boolean> deleteStationById(@PathVariable(name = "id") Integer id) {
        return service.deleteStation(id) ? new ResponseEntity<>(true, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @CrossOrigin
    @PatchMapping("/updateStation/{id}")
    public ResponseEntity<Boolean> updateStationById(@PathVariable(name = "id") Integer id, @RequestBody StationDTO dto) {
        return service.updateStation(id, dto) ? new ResponseEntity<>(true, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
