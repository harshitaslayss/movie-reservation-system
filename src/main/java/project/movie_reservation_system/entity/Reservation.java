package project.movie_reservation_system.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal totalAmount;
    @CreationTimestamp
    private LocalDateTime bookedAt;

    @ManyToOne
    private User user;

    @ManyToOne
    private Show show;

    @OneToMany(mappedBy = "reservation")
    private List<ShowSeat> showSeats;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;



}
