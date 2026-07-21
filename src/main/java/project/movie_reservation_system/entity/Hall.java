package project.movie_reservation_system.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "halls")
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "hall")
    private List<Show> shows;

    @OneToMany(
            mappedBy = "hall",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Seat> seats;

    @ManyToOne
    private Theatre theatre;

}
