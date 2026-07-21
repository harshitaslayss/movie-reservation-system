package project.movie_reservation_system.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TheatreRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String address;
}
