package pe.edu.upc.center.platform.recipes.domain.services;

import pe.edu.upc.center.platform.recipes.domain.model.commands.CreateRecipeTypeCommand;

public interface RecipeTypeCommandService {
    Long handle(CreateRecipeTypeCommand command);
}
