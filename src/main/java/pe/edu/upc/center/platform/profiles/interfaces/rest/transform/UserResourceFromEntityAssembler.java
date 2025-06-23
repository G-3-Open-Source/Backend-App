package pe.edu.upc.center.platform.profiles.interfaces.rest.transform;

import pe.edu.upc.center.platform.profiles.domain.model.aggregates.User;
import pe.edu.upc.center.platform.profiles.interfaces.rest.resources.UserResource;

import java.util.List;
import java.util.stream.Collectors;

public class UserResourceFromEntityAssembler {

    public static UserResource toResourceFromEntity(User user) {
        return new UserResource(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getIsActive(),
                user.getBirthDate(),
                user.getUserProfile().getId()
        );
    }

    public static List<UserResource> toResources(List<User> users) {
        return users.stream().map(UserResourceFromEntityAssembler::toResourceFromEntity).collect(Collectors.toList());
    }
}
