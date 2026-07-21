package project.movie_reservation_system.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import project.movie_reservation_system.entity.BookingStatus;
import project.movie_reservation_system.entity.Show;
import project.movie_reservation_system.entity.ShowSeat;
import project.movie_reservation_system.entity.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReservationRequest {

    @NotNull
    private Long showId;
    @NotEmpty
    private List<Long> showSeatId;

}
