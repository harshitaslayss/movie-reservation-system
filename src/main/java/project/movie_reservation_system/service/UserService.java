package project.movie_reservation_system.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.movie_reservation_system.dto.RegisterRequest;
import project.movie_reservation_system.dto.RegisterResponse;
import project.movie_reservation_system.entity.Role;
import project.movie_reservation_system.entity.User;
import project.movie_reservation_system.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    @Override
    public UserDetails loadUserByUsername( String username) throws UsernameNotFoundException {
        User user= userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with the following email: "+username+ " does not exist."));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_"+ user.getRole())));
    }


    public RegisterResponse registerUser(RegisterRequest request) {
        User user= new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        userRepository.save(user);
        return new RegisterResponse(user.getId(),
                user.getName(),
                user.getEmail(),
                jwtService.generateToken(user.getEmail()),
                user.getRole());
    }
}
