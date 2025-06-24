package pe.edu.upc.center.backendNutriSmart.tracking.application.internal.commandservices;

import pe.edu.upc.center.backendNutriSmart.tracking.domain.model.Entities.MacronutrientValues;
import pe.edu.upc.center.backendNutriSmart.tracking.domain.model.Entities.MealPlanEntry;
import pe.edu.upc.center.backendNutriSmart.tracking.domain.model.Entities.MealPlanType;
import pe.edu.upc.center.backendNutriSmart.tracking.domain.model.Entities.TrackingGoal;
import pe.edu.upc.center.backendNutriSmart.tracking.domain.model.aggregates.Tracking;
import pe.edu.upc.center.backendNutriSmart.tracking.domain.model.commands.CreateMealPlanEntryToTrackingCommand;
import pe.edu.upc.center.backendNutriSmart.tracking.domain.model.commands.CreateTrackingCommand;
import pe.edu.upc.center.backendNutriSmart.tracking.domain.model.commands.RemoveMealPlanEntryFromTrackingCommand;
import pe.edu.upc.center.backendNutriSmart.tracking.domain.model.commands.UpdateMealPlanEntryInTrackingCommand;
import pe.edu.upc.center.backendNutriSmart.tracking.domain.model.valueobjects.MealPlanTypes;
import pe.edu.upc.center.backendNutriSmart.tracking.domain.services.TrackingCommandService;
import pe.edu.upc.center.backendNutriSmart.tracking.infrastructure.persistence.jpa.repositories.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class TrackingCommandServiceImpl implements TrackingCommandService {
    private final TrackingRepository trackingRepository;
    private final MealPlanEntryRepository mealPlanEntryRepository;
    private final TrackingGoalRepository trackingGoalRepository;
    private final MacronutrientValuesRepository macronutrientValuesRepository;
    private final MealPlanTypeRepository mealPlanTypeRepository;

    public TrackingCommandServiceImpl(TrackingRepository trackingRepository, MealPlanEntryRepository mealPlanEntryRepository,
                                      TrackingGoalRepository trackingGoalRepository, MacronutrientValuesRepository macronutrientValuesRepository,
                                      MealPlanTypeRepository mealPlanTypeRepository) {
        this.trackingRepository = trackingRepository;
        this.mealPlanEntryRepository = mealPlanEntryRepository;
        this.trackingGoalRepository = trackingGoalRepository;
        this.macronutrientValuesRepository = macronutrientValuesRepository;
        this.mealPlanTypeRepository = mealPlanTypeRepository;
    }

    @Override
    public Long handle(CreateMealPlanEntryToTrackingCommand command) {
        // Buscar el tracking por ID de usuario
        Optional<Tracking> trackingOpt = trackingRepository.findByUserId(command.userId());

        if (trackingOpt.isEmpty()) {
            throw new IllegalArgumentException("Tracking not found for user: " + command.userId());
        }

        MealPlanType mealPlanType = mealPlanTypeRepository.findByName(command.mealPlanType().getName())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de plan de comida inválido: " + command.mealPlanType()));


        Tracking tracking = trackingOpt.get();

        // Crear nuevo MealPlanEntry
        MealPlanEntry newEntry = new MealPlanEntry(
                command.recipeId(),
                mealPlanType,
                command.DayNumber()
        );

        // Agregar al tracking (esto solo lo agrega a la lista en memoria)
        tracking.addMealPlanEntry(newEntry);

        // NUEVO: Guardar el tracking primero
        Tracking savedTracking = trackingRepository.save(tracking);

        // NUEVO: Ahora guardar la entry con la FK
        newEntry.setTracking(savedTracking);
        mealPlanEntryRepository.save(newEntry);

        return savedTracking.getId();
    }

    @Override
    public void handle(RemoveMealPlanEntryFromTrackingCommand command) {
        // Buscar el tracking
        Optional<Tracking> trackingOpt = trackingRepository.findById(command.TrackingId());

        if (trackingOpt.isEmpty()) {
            throw new IllegalArgumentException("Tracking not found with id: " + command.TrackingId());
        }

        Tracking tracking = trackingOpt.get();

        Optional<MealPlanEntry> mealPlanEntryOpt = mealPlanEntryRepository.findById(command.MealPlanEntryId());

        if (mealPlanEntryOpt.isEmpty()) {
            throw new IllegalArgumentException("MealPlan not found with id: " + command.MealPlanEntryId());
        }

        MealPlanEntry mealPlanEntry = mealPlanEntryOpt.get();

        // Remover el MealPlanEntry
        boolean removed = tracking.removeMealPlanEntry(mealPlanEntry);

        if (!removed) {
            throw new IllegalArgumentException("MealPlanEntry not found with id: " + command.MealPlanEntryId());
        }

        // Guardar el tracking
        trackingRepository.save(tracking);
    }

    @Override
    public Optional<Tracking> handle(UpdateMealPlanEntryInTrackingCommand command) {
        Optional<Tracking> trackingOpt = trackingRepository.findById(command.TrackingId());

        if (trackingOpt.isEmpty()) {
            return Optional.empty();
        }

        Tracking tracking = trackingOpt.get();

        Optional<MealPlanEntry> mealPlanEntryOpt = mealPlanEntryRepository.findById(command.MealPlanEntryId());

        if (mealPlanEntryOpt.isEmpty()) {
            throw new IllegalArgumentException("MealPlan not found with id: " + command.MealPlanEntryId());
        }

        MealPlanEntry mealPlanEntry = mealPlanEntryOpt.get();

        MealPlanType mealPlanType = mealPlanTypeRepository.findByName(command.mealPlanType())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de plan de comida inválido: " + command.mealPlanType()));

        // Remover el MealPlanEntry de memoria Y de BD
        tracking.removeMealPlanEntry(mealPlanEntry);
        mealPlanEntryRepository.delete(mealPlanEntry); // NUEVO

        // Crear nuevo entry con los datos actualizados
        MealPlanEntry updatedEntry = new MealPlanEntry(
                command.recipeId(),
                mealPlanType,
                command.dayNumber()
        );

        tracking.addMealPlanEntry(updatedEntry);

        // NUEVO: Guardar tracking y luego la entry
        Tracking savedTracking = trackingRepository.save(tracking);
        updatedEntry.setTracking(savedTracking);
        mealPlanEntryRepository.save(updatedEntry);

        return Optional.of(savedTracking);
    }

    public Long handle(CreateTrackingCommand command) {
        if(this.trackingRepository.existsByUserId(command.profile())){
            throw new IllegalArgumentException("Tracking already exists for user: " + command.profile());
        }

        Optional<TrackingGoal> trackingGoalOpt = trackingGoalRepository.findByUserId(command.profile());

        if (trackingGoalOpt.isEmpty()) {
            throw new IllegalArgumentException("Tracking goal not found for user: " + command.profile());
        }
        TrackingGoal trackingGoal = trackingGoalOpt.get();
        LocalDate date = LocalDate.now();
        MacronutrientValues consumed = new MacronutrientValues(0, 0, 0, 0);
        macronutrientValuesRepository.save(consumed);

        var tracking = new Tracking(command.profile(),date,trackingGoal, consumed);

        try{
            trackingRepository.save(tracking);
        } catch(Exception e){
            throw new IllegalArgumentException("Error while saving tracking:" + e.getMessage());
        }
        return tracking.getId();
    }
}
