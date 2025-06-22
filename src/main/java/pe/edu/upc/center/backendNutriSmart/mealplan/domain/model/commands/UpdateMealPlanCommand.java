package pe.edu.upc.center.backendNutriSmart.mealplan.domain.model.commands;

public record UpdateMealPlanCommand(
        String name,
        String description,
        float calories,
        float carbs,
        float proteins,
        float fats,
        int userProfileId,
        String tag,
        String category,
        Boolean isCurrent
) {
}
