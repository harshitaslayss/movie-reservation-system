package project.movie_reservation_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TheatreResponse {
    private Long id;
    private String name;
    private String address;
    private String city;
}
