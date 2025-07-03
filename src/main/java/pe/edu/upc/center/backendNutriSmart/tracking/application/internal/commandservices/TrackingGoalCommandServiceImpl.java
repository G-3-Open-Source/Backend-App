package pe.edu.upc.center.backendNutriSmart.tracking.application.internal.commandservices;

import pe.edu.upc.center.backendNutriSmart.tracking.domain.model.Entities.TrackingGoal;
import pe.edu.upc.center.backendNutriSmart.tracking.domain.model.commands.*;
import pe.edu.upc.center.backendNutriSmart.tracking.domain.services.TrackingGoalCommandService;
import pe.edu.upc.center.backendNutriSmart.tracking.infrastructure.persistence.jpa.repositories.TrackingGoalRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TrackingGoalCommandServiceImpl implements TrackingGoalCommandService {

    private final TrackingGoalRepository trackingGoalRepository;

    public TrackingGoalCommandServiceImpl(TrackingGoalRepository trackingGoalRepository) {
        this.trackingGoalRepository = trackingGoalRepository;
    }

    @Transactional
    public Long handle(CreateTrackingGoalCommand command) {
        TrackingGoal trackingGoal = new TrackingGoal(command.profile(),
                command.macronutrientValues()
        );

        TrackingGoal saved = trackingGoalRepository.save(trackingGoal);
        return saved.getId();
    }
}
