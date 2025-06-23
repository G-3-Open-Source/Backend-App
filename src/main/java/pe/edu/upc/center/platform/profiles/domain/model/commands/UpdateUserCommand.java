package pe.edu.upc.center.platform.profiles.domain.model.commands;

public record UpdateUserCommand(
        String name,
        String email,

        String password,
        Boolean isActive,
        String birthDate,
        Long userProfileId
) {
}
