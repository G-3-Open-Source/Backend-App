package pe.edu.upc.center.platform.recipes.interfaces.rest.resources;

public record FavoriteRecipeResource(Long id, Long userId, Long recipeId, String recipeName) {
}
