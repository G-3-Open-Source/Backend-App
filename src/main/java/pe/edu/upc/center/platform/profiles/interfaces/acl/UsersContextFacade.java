package pe.edu.upc.center.platform.profiles.interfaces.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.profiles.domain.services.UserCommandService;
import pe.edu.upc.center.platform.profiles.domain.services.UserQueryService;
import pe.edu.upc.center.platform.profiles.domain.model.commands.CreateUserCommand;
import pe.edu.upc.center.platform.profiles.interfaces.rest.resources.CreateUserResource;
import pe.edu.upc.center.platform.profiles.interfaces.rest.resources.UserResource;
import pe.edu.upc.center.platform.profiles.interfaces.rest.transform.CreateUserCommandFromResourceAssembler;
import pe.edu.upc.center.platform.profiles.interfaces.rest.transform.UserResourceFromEntityAssembler;

import java.util.List;
import java.util.Optional;

@Service
public class UsersContextFacade {
    private final UserCommandService commandService;
    private final UserQueryService queryService;

    public UsersContextFacade(UserCommandService commandService, UserQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    public List<UserResource> fetchAll() {
        return UserResourceFromEntityAssembler.toResources(queryService.getAllUsers());
    }

    public Optional<UserResource> fetchById(Long id) {
        return queryService.getUserById(id).map(UserResourceFromEntityAssembler::toResourceFromEntity);
    }

    public Long create(CreateUserResource resource) {
        CreateUserCommand command = CreateUserCommandFromResourceAssembler.toCommandFromResource(resource);
        return commandService.handle(command).getId();
    }
}
