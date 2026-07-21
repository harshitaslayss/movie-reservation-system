package project.movie_reservation_system.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.movie_reservation_system.dto.*;
import project.movie_reservation_system.service.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name="User")
public class UserController {
    private final MovieService movieService;
    private final ShowService showService;
    private final TheatreService theatreService;
    private final ShowSeatService showSeatService;
    private final ReservationService reservationService;

    //implement movieResponse DTO
    @GetMapping("/movies")
    @Operation(summary = "Get all movies.")
    public ResponseEntity<Page<MovieResponse>> getAllMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5")  int size,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(required = false) String rating,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String language,
            @RequestParam(required = false) String title
    ){
        return ResponseEntity.ok(movieService.getMovies(page,size,sortBy,direction,rating,genre,language,title));
    }

    @Operation(summary = "Get an existing movie by id.")
    @GetMapping("/movies/{id}")
    public ResponseEntity<MovieResponse> getMovie(@PathVariable Long id){
        return ResponseEntity.ok(movieService.getMovie(id));
    }

    //get shows
    @Operation(summary = "Get all shows.")
    @GetMapping("/shows")
    public ResponseEntity<Page<ShowResponse>> getAllShows( @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "5")  int size,
                                                           @RequestParam(defaultValue = "startTime") String sortBy,
                                                           @RequestParam(defaultValue = "asc") String direction,
                                                           @RequestParam(required = false) Long movieId,
                                                           @RequestParam(required = false) Long theatreId,
                                                           @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
                                                           ){
        return ResponseEntity.ok(showService.getShows(page, size, sortBy, direction, movieId, theatreId, date));
    }

    @Operation(summary = "Get show by id.")
    @GetMapping("/shows/{id}")
    public ResponseEntity<ShowResponse> getShow(@PathVariable Long id){
        return ResponseEntity.ok(showService.getShow(id));
    }

    //get theatres
    @Operation(summary = "Get all theatres.")
    @GetMapping("/theatres")
    public ResponseEntity<Page<TheatreResponse>> getTheatres(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "5")  int size,
                                                             @RequestParam(defaultValue = "name") String sortBy,
                                                             @RequestParam(defaultValue = "asc") String direction,
                                                             @RequestParam(required = false) String name,
                                                             @RequestParam(required = false) String city){
        return ResponseEntity.ok(theatreService.getTheatres(page, size, sortBy, direction,name, city));
    }

    //get shows for a particular movie
    @Operation(summary = "Get all shows for a movie.")
    @GetMapping("/movies/{id}/shows")
    public ResponseEntity<List<ShowResponse>> getMovieShows(@PathVariable Long id){
        return ResponseEntity.ok(showService.getMovieShows(id));
    }

    @Operation(summary = "Get all seats for a show.")
    @GetMapping("/shows/{id}/seats")
    public ResponseEntity<List<ShowSeatResponse>> getShowSeats(@PathVariable Long id){
        return ResponseEntity.ok(showSeatService.getShowSeats(id));
    }

    //post reservations
    @Operation(summary = "Create a new reservation.")
    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> createReservation(@Valid @RequestBody ReservationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.createReservation(request));
    }

    //get reservations
    @Operation(summary = "Get all reservations.")
    @GetMapping("/reservations")
    public ResponseEntity<Page<ReservationResponse>> getReservations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "showTime") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ){
        return ResponseEntity.ok(reservationService.getReservations(page, size, sortBy, direction));
    }

    @Operation(summary = "Get reservation by id.")
    @GetMapping("/reservations/{id}")
    public ResponseEntity<ReservationResponse> getReservation(@PathVariable Long id){
        return ResponseEntity.ok(reservationService.getReservation(id));
    }

    @Operation(summary = "Cancel a reservation by id.")
    @PutMapping("/reservations/{id}/cancel")
    public ResponseEntity<String> cancelReservation(@PathVariable Long id){
        return ResponseEntity.ok(reservationService.cancelReservation(id));
    }



}
