package ru.bmstu.server.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "vagon")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Vagon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_v")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_t")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Train train;

    @Column(name = "num_v")
    private Integer number;

    @Column(name = "class_v")
    private String type;
}
