package project.movie_reservation_system.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "show_seat", uniqueConstraints = {@UniqueConstraint(columnNames = {"seat_id","show_id"})})
public class ShowSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Show show;

    @ManyToOne
    private Seat seat;

    @Column(nullable = false)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private SeatStatus status;

    @ManyToOne
    private Reservation reservation;
}
