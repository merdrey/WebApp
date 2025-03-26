package ru.bmstu.server.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "ticket")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_t")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "id_pc")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PassengerCard passenger;

    @OneToOne
    @JoinColumn(name = "id_sc")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SeatCard seat;

    @Column(name = "depst_t")
    private Integer depStation;

    @Column(name = "arrst_t")
    private Integer arrStation;
}
