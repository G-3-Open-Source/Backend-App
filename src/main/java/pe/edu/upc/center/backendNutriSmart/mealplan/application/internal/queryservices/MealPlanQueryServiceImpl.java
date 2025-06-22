package pe.edu.upc.center.backendNutriSmart.mealplan.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.backendNutriSmart.mealplan.domain.model.aggregates.MealPlan;
import pe.edu.upc.center.backendNutriSmart.mealplan.domain.model.queries.GetAllMealPlanByProfileIdQuery;
import pe.edu.upc.center.backendNutriSmart.mealplan.domain.model.queries.GetAllMealPlanQuery;
import pe.edu.upc.center.backendNutriSmart.mealplan.domain.model.queries.GetMealPlanByIdQuery;
import pe.edu.upc.center.backendNutriSmart.mealplan.domain.services.MealPlanQueryService;
import pe.edu.upc.center.backendNutriSmart.mealplan.infrastructure.persistence.jpa.repositories.MealPlanRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MealPlanQueryServiceImpl implements MealPlanQueryService {
    private final MealPlanRepository mealPlanRepository;

    public MealPlanQueryServiceImpl(MealPlanRepository mealPlanRepository) {
        this.mealPlanRepository = mealPlanRepository;
    }
    @Override
    public Optional<MealPlan> handle(GetMealPlanByIdQuery query) {
        return Optional.empty();
    }

    @Override
    public List<MealPlan> handle(GetAllMealPlanQuery query) {
        return List.of();
    }

    @Override
    public List<MealPlan> handle(GetAllMealPlanByProfileIdQuery query) {
        return List.of();
    }
}
