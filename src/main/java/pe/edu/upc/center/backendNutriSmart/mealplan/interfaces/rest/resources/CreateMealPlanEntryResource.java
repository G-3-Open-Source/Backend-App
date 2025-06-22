package pe.edu.upc.center.backendNutriSmart.mealplan.interfaces.rest.resources;

public record CreateMealPlanEntryResource(
        int recipeId,
        int mealPlanTypeId
) {
}
