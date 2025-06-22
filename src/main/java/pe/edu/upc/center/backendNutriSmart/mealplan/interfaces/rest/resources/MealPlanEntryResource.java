package pe.edu.upc.center.backendNutriSmart.mealplan.interfaces.rest.resources;

public record MealPlanEntryResource(
        int id,
        int recipeId,
        String mealPlanType,
        int mealPlanId
) {
}
