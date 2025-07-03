package pe.edu.upc.center.backendNutriSmart.recipes.interfaces.rest.resources;

public record RecipeResource(int id, Long userId, String name, String description, int preparationTime,
                             String difficulty, String category, String recipeType) {
}

