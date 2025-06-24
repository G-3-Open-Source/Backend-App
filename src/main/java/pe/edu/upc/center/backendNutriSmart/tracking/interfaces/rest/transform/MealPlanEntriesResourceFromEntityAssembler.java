// MealPlanEntriesResourceFromEntityAssembler.java
package pe.edu.upc.center.backendNutriSmart.tracking.interfaces.rest.transform;

import pe.edu.upc.center.backendNutriSmart.tracking.domain.model.Entities.MealPlanEntry;
import pe.edu.upc.center.backendNutriSmart.tracking.interfaces.rest.resources.MealPlanEntriesResource;

public class MealPlanEntriesResourceFromEntityAssembler {
    public static MealPlanEntriesResource toResource(MealPlanEntry entity) {
        return new MealPlanEntriesResource(
                entity.getId(),
                entity.getRecipeId().recipeId(),
                entity.getMealPlanType().ToString(),
                entity.getDayNumber()
        );
    }
}