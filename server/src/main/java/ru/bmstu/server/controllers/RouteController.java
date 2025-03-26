package ru.bmstu.server.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.server.dto.RouteDTO;
import ru.bmstu.server.dto.RouteStationDTO;
import ru.bmstu.server.services.impl.RouteServiceImpl;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/route")
public class RouteController {

    private RouteServiceImpl service;

    @CrossOrigin
    @PostMapping("/createRoute")
    public ResponseEntity<RouteDTO> createRoute() {
        return new ResponseEntity<>(service.create(), HttpStatus.CREATED);
    }

    @CrossOrigin
    @GetMapping("/getAllRoutes")
    public ResponseEntity<List<RouteDTO>> getAllRoutes() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping("/deleteRoute/{id}")
    public ResponseEntity<Boolean> deleteRoute(@PathVariable(name = "id") Integer id) {
        return service.delete(id) ? new ResponseEntity<>(true, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
