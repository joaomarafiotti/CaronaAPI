package br.ifsp.demo.security.auth;

import br.ifsp.demo.models.response.UserResponseModel;
import br.ifsp.demo.security.config.JwtService;
import br.ifsp.demo.security.user.Role;
import br.ifsp.demo.usecase.user.GetUserUseCase;
import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1")
@AllArgsConstructor
@Tag(name = "Registration/Authentication API")
public class UserController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final UserAuthorizationVerifier verifier;
    private final GetUserUseCase getUserUseCase;
    private final AuthenticationInfoService authenticationInfoService;

    @Operation(
            summary = "Register a new user.",
            description = "Returns the new user id."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201", description = "Successful operation.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RegisterUserRequest.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication fails.",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "User email is already registered.",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterUserRequest request) {
        final RegisterUserResponse response = authenticationService.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Authenticates the user using email and password.",
            description = "Returns a JWT credential to be used in future requests."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Successful operation.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication fails.",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PostMapping("/authenticate")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        final AuthResponse response = authenticationService.authenticate(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Validate a JWT token.",
            description = "Returns 200 OK if token is valid, otherwise 401 Unauthorized."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Token is valid.",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "401", description = "Token is invalid or expired.",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PostMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            String prefix = "Bearer ";
            if (authorizationHeader == null || !authorizationHeader.startsWith(prefix)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            String token = authorizationHeader.substring(prefix.length());
            String username = jwtService.extractUsername(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            boolean isValid = jwtService.isTokenValid(token, userDetails);
            if (isValid) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (JwtException | UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/user")
    public ResponseEntity<UserResponseModel> getUserByToken() {
        UUID userId = authenticationInfoService.getAuthenticatedUserId();
        return ResponseEntity.ok(getUserUseCase.getUserById(userId));
    }
}
