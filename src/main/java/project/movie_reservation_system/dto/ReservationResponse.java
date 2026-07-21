package project.movie_reservation_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import project.movie_reservation_system.entity.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ReservationResponse {
    private Long id;

    private String movieName;
    private String hallName;
    private LocalDateTime showTime;
    private List<String> showSeats;
    private BigDecimal totalAmount;
    private BookingStatus status;
    private Long UserId;
}
