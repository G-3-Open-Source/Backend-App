package pe.edu.upc.center.backendNutriSmart.mealplan.domain.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.center.backendNutriSmart.mealplan.domain.model.aggregates.MealPlan;

@Getter
@Entity
@Table(name = "meal_plan_tags")
public class MealPlanTag {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;

    @Getter
    @Column(name = "tag", length = 50, nullable = false)
    private String tag;

    @ManyToOne
    @JoinColumn(name = "meal_plan_id")
    private MealPlan mealPlan;
}
