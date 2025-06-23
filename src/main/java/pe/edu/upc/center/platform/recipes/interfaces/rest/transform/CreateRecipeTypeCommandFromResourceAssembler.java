package pe.edu.upc.center.platform.recipes.interfaces.rest.transform;

import pe.edu.upc.center.platform.recipes.domain.model.commands.CreateRecipeTypeCommand;
import pe.edu.upc.center.platform.recipes.interfaces.rest.resources.CreateRecipeTypeResource;

public class CreateRecipeTypeCommandFromResourceAssembler {

    public static CreateRecipeTypeCommand toCommandFromResource(CreateRecipeTypeResource resource) {
        return new CreateRecipeTypeCommand(resource.name());
    }
}
