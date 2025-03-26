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
@Table(name = "routest")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RouteStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rs")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_r")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Route route;

    @OneToOne
    @JoinColumn(name = "id_s")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Station station;

    @Column(name = "arrdate_rs")
    @Temporal(TemporalType.DATE)
    private LocalDate arriveDate;

   @Column(name = "arrtime_rs")
   @Temporal(TemporalType.TIME)
   private LocalTime arriveTime;

   @Column(name = "depdate_rs")
   @Temporal(TemporalType.DATE)
   private LocalDate departureDate;

   @Column(name = "deptime_rs")
   @Temporal(TemporalType.TIME)
   private LocalTime departureTime;
}
