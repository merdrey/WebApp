package ru.bmstu.server.utils;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.bmstu.server.dto.*;
import ru.bmstu.server.entities.*;

@Component
public class Mapper {
    static public TrainDTO TrainToTrainDTO(Train train) {
        return new TrainDTO(train.getId(), train.getType(), train.getRoute().getRId());
    }

    static public StationDTO StationToStationDTO(Station station) {
        return new StationDTO(station.getId(), station.getName());
    }

    static public RouteDTO RouteToRouteDTO(Route route) {
        return new RouteDTO(route.getRId());
    }

    static public RouteStationDTO RSToRSDTO(RouteStation rs) {
        return new RouteStationDTO(rs.getId(), rs.getRoute().getRId(), rs.getStation().getId(), rs.getArriveDate(), rs.getArriveTime(), rs.getDepartureDate(), rs.getDepartureTime());
    }

    static public VagonDTO VagonToVagonDTO(Vagon vagon) {
        return new VagonDTO(vagon.getId(), vagon.getTrain().getId(), vagon.getNumber(), vagon.getType());
    }

    static public SeatCardDTO SCToSCDTO(SeatCard sc) {
        return new SeatCardDTO(sc.getId(), sc.getVagon().getId(), sc.getIsBusy(), sc.getNumber(), sc.getPrice());
    }

    static public PassengerCardDTO PCToPCDTO(PassengerCard pc) {
        return new PassengerCardDTO(pc.getId(), pc.getBirthdate(), pc.getData(), pc.getName(), pc.getUser().getId());
    }

    static public TicketDTO TicketToTicketDTO(Ticket ticket) {
        return new TicketDTO(ticket.getId(), ticket.getPassenger().getId(), ticket.getSeat().getId(), ticket.getDepStation(), ticket.getArrStation());
    }

    static public TicketJournalDTO TJToTJDTO(TicketJournal tj) {
        return new TicketJournalDTO(tj.getId(), tj.getTicket().getId(), tj.getOrderDate(), tj.getOrderTime());
    }

    static public BoardStateDTO BSToBSDTO(BoardState bs) {
        return new BoardStateDTO(bs.getId(), bs.getTicket().getId(), bs.getStatus());
    }

    static public UserDTO UserToUserDTO(User u) {
        return new UserDTO(u.getId(), u.getLogin(), u.getPassword(), u.getRole(), u.getPhone(), u.getEmail());
    }
}
