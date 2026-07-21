package project.movie_reservation_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import project.movie_reservation_system.entity.Seat;
import project.movie_reservation_system.entity.SeatStatus;
import project.movie_reservation_system.entity.SeatType;
import project.movie_reservation_system.entity.Show;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ShowSeatResponse {
private Long id;
private BigDecimal price;
private SeatStatus status;
private String seatNumber;
private SeatType seatType;

}
