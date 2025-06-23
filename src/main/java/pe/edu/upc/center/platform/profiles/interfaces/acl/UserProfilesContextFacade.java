package pe.edu.upc.center.platform.profiles.interfaces.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.profiles.domain.model.commands.CreateUserProfileCommand;
import pe.edu.upc.center.platform.profiles.domain.model.commands.DeleteUserProfileCommand;
import pe.edu.upc.center.platform.profiles.domain.model.commands.UpdateUserProfileCommand;
import pe.edu.upc.center.platform.profiles.domain.model.queries.GetAllUserProfilesQuery;
import pe.edu.upc.center.platform.profiles.domain.model.queries.GetUserProfileByIdQuery;
import pe.edu.upc.center.platform.profiles.domain.services.UserProfileCommandService;
import pe.edu.upc.center.platform.profiles.domain.services.UserProfileQueryService;
import pe.edu.upc.center.platform.profiles.interfaces.rest.resources.CreateUserProfileResource;
import pe.edu.upc.center.platform.profiles.interfaces.rest.resources.UserProfileResource;
import pe.edu.upc.center.platform.profiles.interfaces.rest.transform.CreateUserProfileCommandFromResourceAssembler;
import pe.edu.upc.center.platform.profiles.interfaces.rest.transform.UpdateUserProfileCommandFromResourceAssembler;
import pe.edu.upc.center.platform.profiles.interfaces.rest.transform.UserProfileResourceFromEntityAssembler;

import java.util.List;
import java.util.Optional;

@Service
public class UserProfilesContextFacade {

    private final UserProfileCommandService commandService;
    private final UserProfileQueryService queryService;

    public UserProfilesContextFacade(UserProfileCommandService commandService,
                                     UserProfileQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    public List<UserProfileResource> fetchAll() {
        var entities = queryService.handle(new GetAllUserProfilesQuery());
        return UserProfileResourceFromEntityAssembler.toResources(entities);
    }

    public Optional<UserProfileResource> fetchById(Long id) {
        return queryService.handle(new GetUserProfileByIdQuery(id))
                .map(UserProfileResourceFromEntityAssembler::toResourceFromEntity);
    }

    public Long create(CreateUserProfileResource resource) {
        CreateUserProfileCommand cmd = CreateUserProfileCommandFromResourceAssembler.toCommandFromResource(resource);
        return commandService.handle(cmd);
    }

    public void update(Long id, CreateUserProfileResource resource) {
        UpdateUserProfileCommand cmd = UpdateUserProfileCommandFromResourceAssembler.toCommandFromResource(id, resource);
        commandService.handle(cmd);
    }

    public void delete(Long id) {
        commandService.handle(new DeleteUserProfileCommand(id));
    }
}