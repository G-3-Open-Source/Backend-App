package pe.edu.upc.center.backendNutriSmart.tracking.application.internal.commandservices;

import pe.edu.upc.center.backendNutriSmart.tracking.domain.model.Entities.MealPlanEntry;
import pe.edu.upc.center.backendNutriSmart.tracking.domain.model.Entities.TrackingGoal;
import pe.edu.upc.center.backendNutriSmart.tracking.domain.model.aggregates.Tracking;
import pe.edu.upc.center.backendNutriSmart.tracking.domain.model.commands.*;
import pe.edu.upc.center.backendNutriSmart.tracking.domain.services.TrackingCommandService;
import pe.edu.upc.center.backendNutriSmart.tracking.domain.services.TrackingGoalCommandService;
import pe.edu.upc.center.backendNutriSmart.tracking.infrastructure.persistence.jpa.repositories.MealPlanEntryRepository;
import pe.edu.upc.center.backendNutriSmart.tracking.infrastructure.persistence.jpa.repositories.TrackingGoalRepository;
import pe.edu.upc.center.backendNutriSmart.tracking.infrastructure.persistence.jpa.repositories.TrackingRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

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
