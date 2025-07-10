package pe.edu.upc.center.backendNutriSmart.tracking.application.internal.commandservices;

import pe.edu.upc.center.backendNutriSmart.tracking.application.internal.outboundservices.acl.ExternalProfileService;
import pe.edu.upc.center.backendNutriSmart.tracking.domain.model.Entities.TrackingGoal;
import pe.edu.upc.center.backendNutriSmart.tracking.domain.model.commands.*;
import pe.edu.upc.center.backendNutriSmart.tracking.domain.services.TrackingGoalCommandService;
import pe.edu.upc.center.backendNutriSmart.tracking.infrastructure.persistence.jpa.repositories.TrackingGoalRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TrackingGoalCommandServiceImpl implements TrackingGoalCommandService {

    private final TrackingGoalRepository trackingGoalRepository;
    private final ExternalProfileService externalProfileService;

    public TrackingGoalCommandServiceImpl(TrackingGoalRepository trackingGoalRepository,
                                          ExternalProfileService externalProfileService) {
        this.trackingGoalRepository = trackingGoalRepository;
        this.externalProfileService = externalProfileService;
    }

    @Transactional
    public Long handle(CreateTrackingGoalCommand command) {
        // Validar que el usuario existe antes de crear el tracking goal
        if (!externalProfileService.existsUserProfileById(command.profile().userId())) {
            throw new IllegalArgumentException("Profile profile not found with id: " + command.profile());
        }

        TrackingGoal trackingGoal = new TrackingGoal(command.profile(),
                command.macronutrientValues()
        );

        TrackingGoal saved = trackingGoalRepository.save(trackingGoal);
        return saved.getId();
    }
}
