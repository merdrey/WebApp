package ru.bmstu.server.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.server.dto.TicketJournalDTO;
import ru.bmstu.server.services.impl.TicketJournalServiceImpl;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/ticketJournal")
public class TicketJournalController {

    private TicketJournalServiceImpl service;

    @CrossOrigin
    @PostMapping("/createTJ")
    public ResponseEntity<TicketJournalDTO> createTJ(@RequestBody TicketJournalDTO dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @CrossOrigin
    @GetMapping("/getTJ/{id}")
    public ResponseEntity<TicketJournalDTO> getTJ(@PathVariable(name = "id") Integer id) {
        return new ResponseEntity<>(service.get(id), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/getAllTJ")
    public ResponseEntity<List<TicketJournalDTO>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping("/deleteTJ/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable(name = "id") Integer id) {
        return service.delete(id) ? new ResponseEntity<>(true, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
