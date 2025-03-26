package ru.bmstu.server.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Entity
@Table(name = "seatcard")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SeatCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sc")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_v")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Vagon vagon;

    @Column(name = "isbusy_sc")
    private Boolean isBusy;

    @Column(name = "number_sc")
    private Integer number;

    @Column(name = "price_sc")
    private BigDecimal price;
}
