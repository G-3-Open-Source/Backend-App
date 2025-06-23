package pe.edu.upc.center.platform.profiles.domain.services;

import pe.edu.upc.center.platform.profiles.domain.model.commands.CreateUserCommand;
import pe.edu.upc.center.platform.profiles.domain.model.aggregates.User;

public interface UserCommandService {
    User handle(CreateUserCommand command);
}
