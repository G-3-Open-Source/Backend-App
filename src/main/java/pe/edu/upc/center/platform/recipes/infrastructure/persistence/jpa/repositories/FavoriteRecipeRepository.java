package pe.edu.upc.center.platform.recipes.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.center.platform.recipes.domain.model.entities.FavoriteRecipe;
import pe.edu.upc.center.platform.recipes.domain.model.valueobjects.UserId;

import java.util.List;

@Repository
public interface FavoriteRecipeRepository extends JpaRepository<FavoriteRecipe, Long> {
    List<FavoriteRecipe> findByUserId(UserId userId);
    boolean existsByUserIdAndRecipeId(UserId userId, Long recipeId);
    void deleteByUserIdAndRecipeId(UserId userId, Long recipeId);
}