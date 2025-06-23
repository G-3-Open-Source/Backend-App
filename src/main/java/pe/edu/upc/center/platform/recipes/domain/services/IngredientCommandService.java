package pe.edu.upc.center.platform.recipes.domain.services;

import pe.edu.upc.center.platform.recipes.domain.model.commands.CreateIngredientCommand;
import pe.edu.upc.center.platform.recipes.domain.model.commands.DeleteIngredientCommand;

public interface IngredientCommandService {
    Long handle(CreateIngredientCommand command);
    void handle(DeleteIngredientCommand command);
}
