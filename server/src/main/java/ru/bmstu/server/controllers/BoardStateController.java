package ru.bmstu.server.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.server.dto.BoardStateDTO;
import ru.bmstu.server.services.impl.BoardStateServiceImpl;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/boardState")
public class BoardStateController {
    private BoardStateServiceImpl service;

    @CrossOrigin
    @PostMapping("/createBoardState")
    public ResponseEntity<BoardStateDTO> create(@RequestBody BoardStateDTO dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @CrossOrigin
    @GetMapping("/getAllBS")
    public ResponseEntity<List<BoardStateDTO>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @CrossOrigin
    @PatchMapping("/updateBS/{id}")
    public ResponseEntity<Boolean> update(@PathVariable(name = "id") Integer id, @RequestBody BoardStateDTO dto) {
        return service.update(id, dto) ? new ResponseEntity<>(true, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
