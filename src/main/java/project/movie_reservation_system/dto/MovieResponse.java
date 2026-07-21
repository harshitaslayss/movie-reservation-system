package project.movie_reservation_system.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DurationFormat;
import project.movie_reservation_system.entity.Genre;
import project.movie_reservation_system.entity.Language;
import project.movie_reservation_system.entity.Rating;
import project.movie_reservation_system.entity.Show;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class MovieResponse {
    private Long id;
    private String title;
    private LocalDate releaseDate;
    private String description;
    private Rating rating;
    private Duration duration;
    private Genre genre;
    private Language language;
    private String posterUrl;
}
