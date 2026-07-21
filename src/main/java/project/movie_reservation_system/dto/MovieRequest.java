package project.movie_reservation_system.dto;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.format.annotation.DurationFormat;
import project.movie_reservation_system.entity.Genre;
import project.movie_reservation_system.entity.Language;
import project.movie_reservation_system.entity.Rating;
import project.movie_reservation_system.entity.Show;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class MovieRequest {

    @NotBlank
    private String title;
    @NotNull
    private LocalDate releaseDate;
    @NotBlank
    private String description;
    @NonNull
    private Rating rating;

    @NonNull
    private Duration duration;
    @NonNull
    private Genre genre;
    @NonNull
    private Language language;
    @NotBlank
    private String posterUrl;

}
