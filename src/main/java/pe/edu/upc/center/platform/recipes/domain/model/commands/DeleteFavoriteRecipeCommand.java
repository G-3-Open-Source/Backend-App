package pe.edu.upc.center.platform.recipes.domain.model.commands;

public record DeleteFavoriteRecipeCommand(Long userId, Long recipeId) {
}
