package pe.edu.upc.center.platform.recipes.domain.model.commands;

public record AddIngredientToRecipeCommand(Long recipeId, Long ingredientId) {
}
