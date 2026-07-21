package project.movie_reservation_system.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class SeatException extends RuntimeException{
    String message;
}
