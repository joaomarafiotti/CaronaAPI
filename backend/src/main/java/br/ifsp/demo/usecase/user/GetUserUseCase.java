package br.ifsp.demo.usecase.user;

import br.ifsp.demo.models.response.UserResponseModel;
import br.ifsp.demo.repositories.DriverRepository;
import br.ifsp.demo.repositories.PassengerRepository;
import br.ifsp.demo.security.user.JpaUserRepository;
import br.ifsp.demo.security.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetUserUseCase {
    private final PassengerRepository passengerRepository;
    private final DriverRepository driverRepository;
    private final JpaUserRepository userRepository;

    public UserResponseModel getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id: " + userId + " was not found"))
                .toResponseModel();
    }
}
