package ru.bmstu.server.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.server.dto.VagonDTO;
import ru.bmstu.server.services.impl.VagonServiceImpl;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/vagon")
public class VagonController {

    private VagonServiceImpl service;

    @CrossOrigin
    @PostMapping("/createVagon")
    public ResponseEntity<VagonDTO> create(@RequestBody VagonDTO dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @CrossOrigin
    @GetMapping("/getVagon/{id}")
    public ResponseEntity<VagonDTO> get(@PathVariable(name = "id") Integer id) {
        return new ResponseEntity<>(service.get(id), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/getAllVagons")
    public ResponseEntity<List<VagonDTO>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/getVagonsForTrain/{id}")
    public ResponseEntity<List<VagonDTO>> getVagonsForTrain(@PathVariable(name = "id") Integer id) {
        return new ResponseEntity<>(service.getVagonsForTrain(id), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/getVagonsAndSeatsForTrain/{id}")
    public ResponseEntity<List<Object[]>> getVagonsAndSeatsForTrain(@PathVariable(name = "id") Integer id) {
        return new ResponseEntity<>(service.getVagonsAndSeatsForTrain(id), HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping("/deleteVagon/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable(name = "id") Integer id) {
        return service.delete(id) ? new ResponseEntity<>(true, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @CrossOrigin
    @PatchMapping("/updateVagon/{id}")
    public ResponseEntity<Boolean> update(@PathVariable(name = "id") Integer id, @RequestBody VagonDTO dto) {
        return service.update(id, dto) ? new ResponseEntity<>(true, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
