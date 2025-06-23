package pe.edu.upc.center.platform.recipes.domain.services;

import pe.edu.upc.center.platform.recipes.domain.model.aggregates.Recipe;
import pe.edu.upc.center.platform.recipes.domain.model.commands.CreateRecipeCommand;
import pe.edu.upc.center.platform.recipes.domain.model.commands.DeleteRecipeCommand;
import pe.edu.upc.center.platform.recipes.domain.model.commands.UpdateRecipeCommand;

import java.util.Optional;

public interface RecipeCommandService {
    Long handle(CreateRecipeCommand command);
    Optional<Recipe> handle(UpdateRecipeCommand command);
    void handle(DeleteRecipeCommand command);
}
