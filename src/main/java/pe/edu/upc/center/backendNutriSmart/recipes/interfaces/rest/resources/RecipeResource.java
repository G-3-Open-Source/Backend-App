package pe.edu.upc.center.backendNutriSmart.recipes.interfaces.rest.resources;

public record RecipeResource(Long id, Long userId, String name, String description, int preparationTime,
                             String difficulty, String category, String recipeType) {
}

