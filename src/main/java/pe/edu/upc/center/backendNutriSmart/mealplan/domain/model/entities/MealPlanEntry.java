package pe.edu.upc.center.backendNutriSmart.mealplan.domain.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.center.backendNutriSmart.mealplan.domain.model.aggregates.MealPlan;
import pe.edu.upc.center.backendNutriSmart.mealplan.domain.model.valueobjects.RecipeId;

@Getter
@Entity
@Table(name = "meal_plan_entry")
public class MealPlanEntry {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;

    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name = "recipeId", column = @Column(name = "recipe_id", nullable = false))
    })
    private RecipeId recipeId;

    @ManyToOne
    @JoinColumn(name = "meal_plan_type_id", nullable = false)
    private MealPlanType mealPlanType;

    @ManyToOne
    @JoinColumn(name = "meal_plan_id", nullable = false)
    private MealPlan mealPlan;

}
