package ru.bmstu.server.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "boardstate")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BoardState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bs")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "id_t")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Ticket ticket;

    @Column(name = "status_bs")
    private Boolean status;
}
