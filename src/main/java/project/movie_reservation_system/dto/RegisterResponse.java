package project.movie_reservation_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import project.movie_reservation_system.entity.Role;

@Data
@AllArgsConstructor
public class RegisterResponse {
    Long id;
    String name;
    String email;
    String token;
    project.movie_reservation_system.entity.Role role;
}
