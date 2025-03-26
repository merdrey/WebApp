package ru.bmstu.server.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.server.dto.RouteStationDTO;
import ru.bmstu.server.services.impl.RouteStationServiceImpl;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/routeSt")
public class RouteStationController {

    private RouteStationServiceImpl service;

    @CrossOrigin
    @PostMapping("/createRouteStation")
    public ResponseEntity<RouteStationDTO> create(@RequestBody RouteStationDTO dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @CrossOrigin
    @GetMapping("/getRouteStation/{id}")
    public ResponseEntity<RouteStationDTO> get(@PathVariable(name = "id") Integer id) {
        return new ResponseEntity<>(service.get(id), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/getAllRouteStations")
    public ResponseEntity<List<RouteStationDTO>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/getStationsForRoute/{id}")
    public ResponseEntity<List<RouteStationDTO>> getStationsForRoute(@PathVariable(name = "id") Integer id) {
        return new ResponseEntity<>(service.getStationsForRoute(id), HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping("/deleteRouteStation/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable(name = "id") Integer id) {
        return service.delete(id) ? new ResponseEntity<>(true, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @CrossOrigin
    @PatchMapping("/updateRouteStation/{id}")
    public ResponseEntity<Boolean> update(@PathVariable(name = "id") Integer id, @RequestBody RouteStationDTO dto) {
        return service.update(id, dto) ? new ResponseEntity<>(true, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
