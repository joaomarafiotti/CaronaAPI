package br.ifsp.demo.security.auth;

import br.ifsp.demo.exception.UnauthorizedUserException;
import br.ifsp.demo.repositories.DriverRepository;
import br.ifsp.demo.security.user.JpaUserRepository;
import br.ifsp.demo.security.user.Role;
import br.ifsp.demo.security.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserAuthorizationVerifier {
    private final AuthenticationInfoService authenticationInfoService;
    private final DriverRepository driverRepository;
    private final DriverRepository passengerRepository;
    private final JpaUserRepository jpaUserRepository;

    public UUID verifyAndReturnUuidOf(Role role) {
        UUID userId = authenticationInfoService.getAuthenticatedUserId();
        User user = jpaUserRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id:" + userId + " not found"));
        if (user.getRole() != role)
            throw new UnauthorizedUserException("This user cannot access this resource");
        return userId;
    }

}
