package project.movie_reservation_system.dto;

import lombok.Data;
import project.movie_reservation_system.entity.Role;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private Role role;

}
