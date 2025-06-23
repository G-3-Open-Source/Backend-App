package pe.edu.upc.center.platform.profiles.application.internal.commandservices;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.center.platform.profiles.domain.model.aggregates.User;
import pe.edu.upc.center.platform.profiles.domain.model.aggregates.UserProfile;
import pe.edu.upc.center.platform.profiles.domain.model.commands.CreateUserCommand;
import pe.edu.upc.center.platform.profiles.domain.services.UserCommandService;
import pe.edu.upc.center.platform.profiles.infrastructure.persistence.jpa.repositories.UserProfileRepository;
import pe.edu.upc.center.platform.profiles.infrastructure.persistence.jpa.repositories.UserRepository;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    public UserCommandServiceImpl(UserRepository userRepository, UserProfileRepository userProfileRepository) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    @Transactional
    public User handle(CreateUserCommand command) {
        UserProfile userProfile = userProfileRepository.findById(command.userProfileId())
                .orElseThrow(() -> new IllegalArgumentException("UserProfile no encontrado con id: " + command.userProfileId()));

        User user = new User(
                command.name(),
                command.email(),
                command.password(),
                command.isActive(),
                command.birthDate(),
                userProfile
        );
        return userRepository.save(user);
    }
}
