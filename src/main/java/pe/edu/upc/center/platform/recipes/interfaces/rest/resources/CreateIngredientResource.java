package pe.edu.upc.center.platform.recipes.interfaces.rest.resources;

public record CreateIngredientResource(String name, double calories, double proteins, double fats,
                                       double carbohydrates, Long macronutrientValuesId) {
}
