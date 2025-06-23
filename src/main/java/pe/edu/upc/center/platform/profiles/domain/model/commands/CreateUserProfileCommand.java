package pe.edu.upc.center.platform.profiles.domain.model.commands;


import java.util.List;

public record CreateUserProfileCommand(
        String gender,
        double height,
        double weight,
        int userScore,
        Long activityLevelId,
        Long objectiveId,
        List<Long> allergyIds

) {
}
