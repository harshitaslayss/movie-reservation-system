package project.movie_reservation_system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import project.movie_reservation_system.dto.AuthResponse;
import project.movie_reservation_system.dto.LoginRequest;
import project.movie_reservation_system.dto.RegisterRequest;
import project.movie_reservation_system.dto.RegisterResponse;
import project.movie_reservation_system.service.JwtService;
import project.movie_reservation_system.service.UserService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Operation(summary = "Default home page (needs no login).")
    @GetMapping("/home")
    public String home(){
        return "home page. no login required.";
    }

    //we need to implement the responseEntity version for this and also add a new user;
    @PostMapping("/auth/register")
    @Operation(summary = "Register using name, email and password.")
    public ResponseEntity<RegisterResponse> registerUser( @RequestBody RegisterRequest request){
         return ResponseEntity.status(HttpStatus.CREATED)
                 .body(userService.registerUser(request));
    }

    @PostMapping("/auth/login")
    @ApiResponse(responseCode = "200",description = "Login successful.",
    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = LoginRequest.class))})
    @Operation(summary = "Login using registered email and password. ")
    public ResponseEntity<AuthResponse> login( @RequestBody LoginRequest request){
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
        );

        if(authentication.isAuthenticated()){
            String token= jwtService.generateToken(request.getEmail());
            return ResponseEntity.ok(new AuthResponse(token));
        }else{
            throw new UsernameNotFoundException("Username is not valid.");
        }
    }


}
