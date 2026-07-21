package project.movie_reservation_system.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@Entity
@Table(name = "shows")
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat
    private LocalDateTime startTime;

    @DateTimeFormat
    private LocalDateTime endTime;

    @ManyToOne
    private Hall hall;

    @ManyToOne
    private Movie movie;

    @OneToMany(mappedBy = "show")
    private List<Reservation> reservations;

}
