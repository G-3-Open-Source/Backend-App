package pe.edu.upc.center.platform.profiles.interfaces.rest.resources;

public record UserResource(
        Long id,
        String name,
        String email,
        String password,
        Boolean isActive,
        String birthDate,
        Long userProfileId
) {}
