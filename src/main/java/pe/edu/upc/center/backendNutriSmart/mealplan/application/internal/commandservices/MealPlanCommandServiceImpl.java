package pe.edu.upc.center.backendNutriSmart.mealplan.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.backendNutriSmart.mealplan.domain.model.aggregates.MealPlan;
import pe.edu.upc.center.backendNutriSmart.mealplan.domain.model.commands.CreateMealPlanCommand;
import pe.edu.upc.center.backendNutriSmart.mealplan.domain.model.commands.DeleteMealPlanCommand;
import pe.edu.upc.center.backendNutriSmart.mealplan.domain.model.commands.UpdateMealPlanCommand;
import pe.edu.upc.center.backendNutriSmart.mealplan.domain.services.MealPlanCommandService;
import pe.edu.upc.center.backendNutriSmart.mealplan.infrastructure.persistence.jpa.repositories.MealPlanRepository;

import java.util.Optional;

@Service
public class MealPlanCommandServiceImpl implements MealPlanCommandService {
    private final MealPlanRepository mealPlanRepository;

    public MealPlanCommandServiceImpl(MealPlanRepository mealPlanRepository) {
        this.mealPlanRepository = mealPlanRepository;
    }

    @Override
    public Optional<MealPlan> handle(CreateMealPlanCommand command) {
        return Optional.empty();
    }

    @Override
    public void handle(DeleteMealPlanCommand command) {
        if(command == null || command.mealPlanId() <= 0) {
            throw new IllegalArgumentException("MealPlan ID not found");
        }
        var optionalMealPlan = this.mealPlanRepository.findById(command.mealPlanId());
        this.mealPlanRepository.deleteById(optionalMealPlan.get().getId());

    }

    @Override
    public Optional<MealPlan> handle(UpdateMealPlanCommand command) {
        return Optional.empty();
    }
}
