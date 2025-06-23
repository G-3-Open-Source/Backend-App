package pe.edu.upc.center.platform.recipes.aplication.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.recipes.domain.model.aggregates.Recipe;
import pe.edu.upc.center.platform.recipes.domain.model.commands.CreateRecipeCommand;
import pe.edu.upc.center.platform.recipes.domain.model.commands.DeleteRecipeCommand;
import pe.edu.upc.center.platform.recipes.domain.model.commands.UpdateRecipeCommand;
import pe.edu.upc.center.platform.recipes.domain.services.RecipeCommandService;
import pe.edu.upc.center.platform.recipes.infrastructure.persistence.jpa.repositories.CategoryRepository;
import pe.edu.upc.center.platform.recipes.infrastructure.persistence.jpa.repositories.RecipeRepository;
import pe.edu.upc.center.platform.recipes.infrastructure.persistence.jpa.repositories.RecipeTypeRepository;

import java.util.Optional;

@Service
public class RecipeCommandServiceImpl implements RecipeCommandService {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final RecipeTypeRepository recipeTypeRepository;

    public RecipeCommandServiceImpl(RecipeRepository recipeRepository, CategoryRepository categoryRepository,  RecipeTypeRepository recipeTypeRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.recipeTypeRepository = recipeTypeRepository;
    }

    @Override
    public Long handle(CreateRecipeCommand command) {
        // Validar si ya existe una receta con el mismo nombre
        if (recipeRepository.existsByName(command.name())) {
            throw new IllegalArgumentException("A recipe with the name " + command.name() + " already exists.");
        }

        var category = categoryRepository.findByName(command.categoryName())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        var recipeType = recipeTypeRepository.findByName(command.recipeTypeName())
                .orElseThrow(() -> new IllegalArgumentException("Recipe type not found"));

        var recipe = new Recipe(
                command.userId(),
                command.name(),
                command.description(),
                command.preparationTime(),
                command.difficulty(),
                category,
                recipeType
        );


        try {
            this.recipeRepository.save(recipe);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving enrollment: " + e.getMessage());
        }

        return recipe.getId();
    }


    @Override
    public Optional<Recipe> handle(UpdateRecipeCommand command) {
        var optionalRecipe = recipeRepository.findById(command.recipeId());
        if (optionalRecipe.isEmpty()) {
            throw new IllegalArgumentException("Recipe not found");
        }

        var recipe = optionalRecipe.get();

        // Validar si el nuevo nombre ya existe en otra receta
        if (!recipe.getName().equals(command.name()) && recipeRepository.existsByName(command.name())) {
            throw new IllegalArgumentException("Another recipe with the name " + command.name() + " already exists.");
        }

        var category = categoryRepository.findByName(command.categoryName())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        var recipeType = recipeTypeRepository.findByName(command.recipeTypeName())
                .orElseThrow(() -> new IllegalArgumentException("Recipe type not found"));

        // Actualizar los datos
        recipe.updateRecipe(
                command.name(),
                command.description(),
                command.preparationTime(),
                command.difficulty(),
                category,
                recipeType
        );

        try {
            this.recipeRepository.save(recipe);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving enrollment: " + e.getMessage());
        }
        return Optional.of(recipe);
    }


    @Override
    public void handle(DeleteRecipeCommand command) {
        if (!recipeRepository.existsById(command.recipeId())) {
            throw new IllegalArgumentException("Recipe not found");
        }
        try {
            this.recipeRepository.deleteById(command.recipeId());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving enrollment: " + e.getMessage());
        }
    }
}
