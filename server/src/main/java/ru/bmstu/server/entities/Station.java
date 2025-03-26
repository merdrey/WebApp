package ru.bmstu.server.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "station")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_s")
    private Integer id;

    @Column(name = "name_s")
    private String name;
}
