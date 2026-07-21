package project.movie_reservation_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HallResponse {
    private Long id;
    private String name;
    private String theatreName;
    private Long totalSeats;
}
