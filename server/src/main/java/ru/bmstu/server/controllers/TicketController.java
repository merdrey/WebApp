package ru.bmstu.server.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.server.dto.TicketDTO;
import ru.bmstu.server.services.impl.TicketServiceImpl;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/ticket")
public class TicketController {

    private TicketServiceImpl service;

    @CrossOrigin
    @PostMapping("/createTicket")
    public ResponseEntity<TicketDTO> create(@RequestBody TicketDTO dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @CrossOrigin
    @GetMapping("/getTicket/{id}")
    public ResponseEntity<TicketDTO> get(@PathVariable(name = "id") Integer id) {
        return new ResponseEntity<>(service.get(id), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/getBS/{trId}/{vId}")
    public ResponseEntity<List<Object[]>> getBS(@PathVariable(name = "trId") Integer trId, @PathVariable(name = "vId") Integer vId) {
        return new ResponseEntity<>(service.getBoardState(trId, vId), HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping("deleteTicket/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable(name = "id") Integer id) {
        return service.delete(id) ? new ResponseEntity<>(true, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
