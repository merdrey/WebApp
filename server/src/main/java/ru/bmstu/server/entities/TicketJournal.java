package ru.bmstu.server.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "ticketjournal")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TicketJournal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tj")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "id_t")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Ticket ticket;

    @Column(name = "orderdate_tj")
    @Temporal(TemporalType.DATE)
    private LocalDate orderDate;

    @Column(name = "ordertime_tj")
    @Temporal(TemporalType.TIME)
    private LocalTime orderTime;
}
