package project.movie_reservation_system.dto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class ShowRequest {

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private Long hallId;

    @NotNull
    private Long movieId;

}
