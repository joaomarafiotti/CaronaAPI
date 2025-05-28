package br.ifsp.demo.security.auth;

import br.ifsp.demo.domain.Driver;
import br.ifsp.demo.domain.Passenger;
import br.ifsp.demo.exception.ApiExceptionHandler;
import br.ifsp.demo.exception.EntityAlreadyExistsException;
import br.ifsp.demo.repositories.DriverRepository;
import br.ifsp.demo.repositories.PassengerRepository;
import br.ifsp.demo.security.config.JwtService;
import br.ifsp.demo.security.user.JpaUserRepository;
import br.ifsp.demo.security.user.Role;
import br.ifsp.demo.security.user.User;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final JpaUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final DriverRepository driverRepository;
    private final PassengerRepository passengerRepository;

    public RegisterUserResponse register(RegisterUserRequest request) {

        userRepository.findByEmail(request.email()).ifPresent(unused -> {
            throw new EntityAlreadyExistsException("Email already registered: " + request.email());});

        String encryptedPassword = passwordEncoder.encode(request.password());

        if(request.role() == Role.PASSENGER) {
            Passenger passenger = new Passenger(request.name(), request.lastname(), request.email(),
                    encryptedPassword,  request.cpf(), request.bithDate());
            passengerRepository.save(passenger);
            return new RegisterUserResponse(passenger.getId());
        }
        if(request.role() == Role.DRIVER) {
            Driver driver = new Driver(request.name(), request.lastname(), request.email(),
                    encryptedPassword,  request.cpf(), request.bithDate());
            driverRepository.save(driver);
            return new RegisterUserResponse(driver.getId());
        }
        throw new IllegalArgumentException("Invalid role");
    }

    public AuthResponse authenticate(AuthRequest request) {
        final var authentication = new UsernamePasswordAuthenticationToken(request.username(), request.password());
        authenticationManager.authenticate(authentication);

        final User user = userRepository.findByEmail(request.username()).orElseThrow();
        final String token = jwtService.generateToken(user);

        return new AuthResponse(token);
    }
}
