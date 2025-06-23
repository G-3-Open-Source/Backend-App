package pe.edu.upc.center.platform.profiles.domain.model.commands;

public record UpdateUserProfileCommand(
        Long userProfileId,
        String gender,
        double height,
        double weight,
        int userScore,
        Long activityLevelId,
        Long objectiveId


) {
}
