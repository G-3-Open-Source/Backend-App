package pe.edu.upc.center.platform.profiles.interfaces.rest.transform;

import pe.edu.upc.center.platform.profiles.domain.model.commands.CreateUserCommand;
import pe.edu.upc.center.platform.profiles.interfaces.rest.resources.CreateUserResource;

public class CreateUserCommandFromResourceAssembler {
    public static CreateUserCommand toCommandFromResource(CreateUserResource r) {
        return new CreateUserCommand(
                r.name(),
                r.email(),
                r.password(),
                r.isActive(),
                r.birthDate(),
                r.userProfileId()
        );
    }
}
