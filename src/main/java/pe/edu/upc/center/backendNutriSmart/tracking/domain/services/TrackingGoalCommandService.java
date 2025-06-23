package pe.edu.upc.center.backendNutriSmart.tracking.domain.services;


import pe.edu.upc.center.backendNutriSmart.tracking.domain.model.commands.CreateTrackingGoalCommand;

public interface TrackingGoalCommandService {
    Long handle(CreateTrackingGoalCommand command);
}
