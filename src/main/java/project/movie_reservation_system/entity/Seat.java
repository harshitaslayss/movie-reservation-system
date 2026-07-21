package project.movie_reservation_system.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "seat")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String seatNumber;

    @ManyToOne
    private Hall hall;

    @Enumerated(EnumType.STRING)
    private SeatType seatType;



}
