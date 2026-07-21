package project.movie_reservation_system.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DurationFormat;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    private LocalDate releaseDate;
    private String description;
    @Enumerated(EnumType.STRING)
    private Rating rating;

    @DurationFormat
    private Duration duration;

    @Enumerated(EnumType.STRING)
    private Genre genre;
    @Enumerated(EnumType.STRING)
    private Language language;

    @OneToMany(mappedBy = "movie")
    private List<Show> shows;

    private String posterUrl;


}
