package pe.edu.upc.center.backendNutriSmart.profiles.domain.model.commands;

public record CreateUserCommand(
        String name,
        String email,

        String password,
        Boolean isActive,
        String birthDate,
        Long userProfileId
) {
}
