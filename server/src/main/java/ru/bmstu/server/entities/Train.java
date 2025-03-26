package ru.bmstu.server.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "train")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_t")
    private Integer id;

    @Column(name = "type_t")
    private String type;

    @OneToOne
    @JoinColumn(name = "id_r")
    private Route route;

}
