package pe.edu.upc.center.platform.recipes.domain.model.commands;

public record CreateRecipeCommand(
        Long userId,
        String name,
        String description,
        int preparationTime,
        String difficulty,
        String categoryName,
        String recipeTypeName
) { }
