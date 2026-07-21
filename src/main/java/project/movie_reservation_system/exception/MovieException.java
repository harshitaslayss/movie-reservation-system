package project.movie_reservation_system.exception;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class MovieException extends RuntimeException{
    String message;
}
