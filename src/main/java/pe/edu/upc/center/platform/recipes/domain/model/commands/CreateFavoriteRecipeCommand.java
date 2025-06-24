package pe.edu.upc.center.platform.recipes.domain.model.commands;

public record CreateFavoriteRecipeCommand(Long userId, Long recipeId) {
}
