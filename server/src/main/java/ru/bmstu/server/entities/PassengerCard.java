package ru.bmstu.server.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Table(name = "passangercard")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PassengerCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pc")
    private Integer id;

    @Column(name = "birthdate_pc")
    @Temporal(TemporalType.DATE)
    private LocalDate birthdate;

    @Column(name = "data_ps")
    private String data;

    @Column(name = "name_pc")
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_u")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
}
