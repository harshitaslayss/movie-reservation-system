package project.movie_reservation_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HallRequest {

    @NotBlank
    private String name;


    @NotNull
    private Long theatreId;

    @NotNull
    private Long rows;

    @NotNull
    private Long seatsPerRow;

}
