package pe.edu.upc.center.platform.recipes.domain.services;

import pe.edu.upc.center.platform.recipes.domain.model.commands.CreateCategoryCommand;

public interface CategoryCommandService {
    Long handle(CreateCategoryCommand command);
}
