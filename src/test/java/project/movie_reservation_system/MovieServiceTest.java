package project.movie_reservation_system;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.movie_reservation_system.dto.MovieRequest;
import project.movie_reservation_system.dto.MovieResponse;
import project.movie_reservation_system.entity.Genre;
import project.movie_reservation_system.entity.Language;
import project.movie_reservation_system.entity.Movie;
import project.movie_reservation_system.entity.Rating;
import project.movie_reservation_system.exception.MovieException;
import project.movie_reservation_system.repository.MovieRepository;
import project.movie_reservation_system.service.MovieService;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @Mock
    MovieRepository movieRepository;

    @InjectMocks
    MovieService movieService;

    @Test
    void create_movie_success(){
        ArgumentCaptor<Movie> argumentCaptor= ArgumentCaptor.forClass(Movie.class);

        MovieRequest request= new MovieRequest();
        request.setTitle("Inception");
        request.setReleaseDate(LocalDate.of(2010, 7, 16));
        request.setDescription("A skilled thief enters people's dreams to steal secrets.");
        request.setRating(Rating.UA);
        request.setDuration(Duration.ofMinutes(148));
        request.setGenre(Genre.ACTION);
        request.setLanguage(Language.HINDI);
        request.setPosterUrl("https://example.com/posters/inception.jpg");


        //create new movie and set args
        Movie savedMovie = new Movie();
        savedMovie.setId(1L);
        savedMovie.setTitle(request.getTitle());
        savedMovie.setReleaseDate(request.getReleaseDate());
        savedMovie.setDescription(request.getDescription());
        savedMovie.setRating(request.getRating());
        savedMovie.setDuration(request.getDuration());
        savedMovie.setGenre(request.getGenre());
        savedMovie.setLanguage(request.getLanguage());
        savedMovie.setPosterUrl(request.getPosterUrl());

        when(movieRepository.save(any(Movie.class))).thenReturn(savedMovie);
        MovieResponse response= movieService.createMovie(request);

        verify(movieRepository).save(argumentCaptor.capture());
        Movie captorMovie= argumentCaptor.getValue();

        assertEquals("Inception",captorMovie.getTitle());
        assertEquals(LocalDate.of(2010, 7, 16), captorMovie.getReleaseDate());
        assertEquals("A skilled thief enters people's dreams to steal secrets.", captorMovie.getDescription());
        assertEquals(Rating.UA, captorMovie.getRating());
        assertEquals(Duration.ofMinutes(148),captorMovie.getDuration());
        assertEquals(Genre.ACTION,captorMovie.getGenre());
        assertEquals(Language.HINDI,captorMovie.getLanguage());
        assertEquals("https://example.com/posters/inception.jpg",captorMovie.getPosterUrl());

        assertEquals(1L, response.getId());
        assertEquals("Inception", response.getTitle());
        assertEquals(Rating.UA, response.getRating());
        assertEquals(Language.HINDI, response.getLanguage());
        assertEquals(Genre.ACTION, response.getGenre());

    }

    @Test
    void get_movie_success(){

        Movie movie= new Movie();
        movie.setId(1L);
        movie.setTitle("Inception");
        movie.setReleaseDate(LocalDate.of(2010, 7, 16));
        movie.setDescription("A skilled thief enters people's dreams to steal secrets.");
        movie.setRating(Rating.UA);
        movie.setDuration(Duration.ofMinutes(148));
        movie.setGenre(Genre.ACTION);
        movie.setLanguage(Language.HINDI);
        movie.setPosterUrl("https://example.com/posters/inception.jpg");

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        MovieResponse response= movieService.getMovie(1L);
        verify(movieRepository).findById(1L);

        assertEquals(1L, response.getId());
        assertEquals("Inception", response.getTitle());
        assertEquals(Rating.UA, response.getRating());
        assertEquals(Language.HINDI, response.getLanguage());
        assertEquals(Genre.ACTION, response.getGenre());
        assertEquals(Duration.ofMinutes(148), response.getDuration());

    }

    @Test
    void get_movie_exception(){
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());
        MovieException exception= assertThrows(MovieException.class,
                () -> movieService.getMovie(1L));
        assertEquals("Movie not found", exception.getMessage());
        verify(movieRepository).findById(1L);
    }

    @Test
    void update_movie_success(){

        ArgumentCaptor<Movie> movieArgumentCaptor= ArgumentCaptor.forClass(Movie.class);

        MovieRequest request = new MovieRequest();
        request.setTitle("Interstellar");
        request.setReleaseDate(LocalDate.of(2014, 11, 7));
        request.setDescription("A team travels through a wormhole.");
        request.setRating(Rating.UA);
        request.setDuration(Duration.ofMinutes(169));
        request.setGenre(Genre.SCI_FI);
        request.setLanguage(Language.ENGLISH);
        request.setPosterUrl("poster-url");

        Movie existingMovie = new Movie();
        existingMovie.setId(1L);
        existingMovie.setTitle("Old Title");

        when(movieRepository.findById(1L)).thenReturn(Optional.of(existingMovie));
        when(movieRepository.save(any(Movie.class)))
                .thenReturn(existingMovie);


        MovieResponse response = movieService.updateMovie(1L, request);
        verify(movieRepository).findById(1L);
        verify(movieRepository).save(movieArgumentCaptor.capture());
        Movie captorMovie= movieArgumentCaptor.getValue();

        assertEquals("Interstellar",captorMovie.getTitle());

        assertEquals("Interstellar", response.getTitle());
        assertEquals(Genre.SCI_FI, response.getGenre());
        assertEquals(Language.ENGLISH, response.getLanguage());
    }

    @Test
    void delete_movie_not_found() {

        when(movieRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(MovieException.class,
                () -> movieService.deleteMovie(1L));

        verify(movieRepository).findById(1L);
        verify(movieRepository, never()).delete((Movie) any());
    }
}
