package project.movie_reservation_system.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ShowException extends RuntimeException{
    String message;
}
