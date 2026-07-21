package project.movie_reservation_system;

import org.hibernate.sql.ast.tree.expression.Over;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import project.movie_reservation_system.dto.MovieRequest;
import project.movie_reservation_system.dto.MovieResponse;
import project.movie_reservation_system.entity.Genre;
import project.movie_reservation_system.entity.Language;
import project.movie_reservation_system.entity.Movie;
import project.movie_reservation_system.entity.Rating;
import project.movie_reservation_system.repository.MovieRepository;
import project.movie_reservation_system.service.MovieService;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class MovieReservationSystemApplicationTests {


}


