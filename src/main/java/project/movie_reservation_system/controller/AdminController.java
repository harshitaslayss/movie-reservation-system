package project.movie_reservation_system.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin")
public class AdminController {

private final MovieService movieService;
private final HallService hallService;
private final TheatreService theatreService;
private final ShowService showService;
private final ReservationService reservationService;

//implement movieResponse
    @Operation(summary = "Create a new movie.")
    @PostMapping("/movies")
    public ResponseEntity<MovieResponse> createMovie(@Valid @RequestBody MovieRequest movieRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.createMovie(movieRequest));
    }

    @Operation(summary = "Update an existing movie.")
    @PutMapping("/movies/{id}")
    public ResponseEntity<MovieResponse> updateMovie(@PathVariable Long id, @Valid @RequestBody MovieRequest movieRequest){
        return ResponseEntity.ok(movieService.updateMovie(id,movieRequest));
    }

    @Operation(summary = "Delete an existing movie.")
    @DeleteMapping("/movies/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable Long id){
        return ResponseEntity.ok(movieService.deleteMovie(id));
    }

    @Operation(summary = "Get all movies.")
    @GetMapping("/movies")
    public ResponseEntity<Page<MovieResponse>> getMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(required = false) String rating,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String language,
            @RequestParam(required = false) String title

    ){
        return ResponseEntity.ok(movieService.getMovies(page,size,sortBy,direction,rating,genre,language,title));
    }


    //for theatre-> return theatre response instd of string
    @Operation(summary = "Create a new Theatre.")
    @PostMapping("/theatres")
    public ResponseEntity<TheatreResponse> createTheatre(@Valid @RequestBody TheatreRequest theatreRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(theatreService.createTheatre(theatreRequest));
    }

    @Operation(summary = "Update an existing theatre.")
    @PutMapping("/theatres/{id}")
    public ResponseEntity<TheatreResponse> updateTheatre(@PathVariable Long id, @Valid @RequestBody TheatreRequest theatreRequest){
        return ResponseEntity.ok(theatreService.updateTheatre(id,theatreRequest));
    }

    @Operation(summary = "Delete an existing theatre.")
    @DeleteMapping("/theatres/{id}")
    public ResponseEntity<String> deleteTheatre(@PathVariable Long id){
        return ResponseEntity.ok(theatreService.deleteTheatre(id));
    }

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

    //for hall
    @Operation(summary = "Create a new hall.")
    @PostMapping("/halls")
    public ResponseEntity<HallResponse> createHall(@Valid @RequestBody HallRequest hallRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(hallService.createHall(hallRequest));
    }

    @Operation(summary = "Update an existing hall.")
    @PutMapping("/halls/{id}")
    public ResponseEntity<HallResponse> updateHall(@PathVariable Long id, @Valid @RequestBody HallRequest hallRequest){
        return ResponseEntity.ok(hallService.updateHall(id,hallRequest));
    }

    @Operation(summary = "Delete an existing hall.")
    @DeleteMapping("/halls/{id}")
    public ResponseEntity<String> deleteHall(@PathVariable Long id){
        return ResponseEntity.ok(hallService.deleteHall(id));
    }

    @Operation(summary = "Get all halls.")
    @GetMapping("/halls")
    public ResponseEntity<Page<HallResponse>> getHalls(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "theatreName")  String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String theatreName

    ){
        return ResponseEntity.ok(hallService.getHalls(page,size,sortBy,direction,name,theatreName));
    }

    //for shows
    @Operation(summary = "Create a new show.")
    @PostMapping("/shows")
    public ResponseEntity<ShowResponse> createShow(@Valid @RequestBody ShowRequest showRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(showService.createShow(showRequest));
    }

    @Operation(summary = "Update an existing show.")
    @PutMapping("/shows/{id}")
    public ResponseEntity<ShowResponse> updateShow(@PathVariable Long id, @Valid @RequestBody ShowRequest showRequest){
        return ResponseEntity.ok(showService.updateShow(id,showRequest));
    }

    @Operation(summary = "Delete an existing show.")
    @DeleteMapping("/shows/{id}")
    public ResponseEntity<String> deleteShow(@PathVariable Long id){
        return ResponseEntity.ok(showService.deleteShow(id));
    }

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

    @Operation(summary = "Get all reservations.")
    @GetMapping("/reservations")
    public ResponseEntity<Page<ReservationResponse>> getAllReservations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "showTime") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ){
        return ResponseEntity.ok(reservationService.getAllReservations(page,size,sortBy,direction));
    }


}
