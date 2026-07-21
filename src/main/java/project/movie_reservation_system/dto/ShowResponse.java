package project.movie_reservation_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import project.movie_reservation_system.entity.Hall;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ShowResponse {
    private Long id;
    private LocalDateTime startTime;
    private Duration duration;
    private String hallName;
    private String theatreName;
    private String movieName;

}
