package pe.edu.upc.center.platform.recipes.domain.model.commands;

public record UpdateRecipeCommand(
        Long recipeId,
        String name,
        String description,
        int preparationTime,
        String difficulty,
        String categoryName,
        String recipeTypeName
) { }
