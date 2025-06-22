package pe.edu.upc.center.backendNutriSmart.mealplan.domain.services;

import pe.edu.upc.center.backendNutriSmart.mealplan.domain.model.aggregates.MealPlan;
import pe.edu.upc.center.backendNutriSmart.mealplan.domain.model.queries.GetAllMealPlanByProfileIdQuery;
import pe.edu.upc.center.backendNutriSmart.mealplan.domain.model.queries.GetAllMealPlanQuery;
import pe.edu.upc.center.backendNutriSmart.mealplan.domain.model.queries.GetMealPlanByIdQuery;

import java.util.List;
import java.util.Optional;

public interface MealPlanQueryService {
    Optional<MealPlan> handle(GetMealPlanByIdQuery query);
    List<MealPlan> handle(GetAllMealPlanQuery query);
    List<MealPlan> handle(GetAllMealPlanByProfileIdQuery query);
}
