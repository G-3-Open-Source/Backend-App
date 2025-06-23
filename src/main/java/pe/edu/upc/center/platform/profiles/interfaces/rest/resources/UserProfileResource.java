package pe.edu.upc.center.platform.profiles.interfaces.rest.resources;
import lombok.Getter;

import java.util.List;
public record UserProfileResource(
        Long id,
        String gender,
        double height,
        double weight,
        int userScore,
        Long activityLevelId,
        String activityLevelName,
        int objectiveId,
        String objectiveName,
        List<String> allergyNames
) {


    public Long getId() {
        return id;
    }
}