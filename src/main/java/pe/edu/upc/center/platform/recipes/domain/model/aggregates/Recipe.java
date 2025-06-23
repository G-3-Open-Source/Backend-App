package pe.edu.upc.center.platform.recipes.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import pe.edu.upc.center.platform.recipes.domain.model.entities.Category;
import pe.edu.upc.center.platform.recipes.domain.model.entities.RecipeType;
import pe.edu.upc.center.platform.recipes.domain.model.valueobjects.UserId;
import pe.edu.upc.center.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Getter
@Entity
@Table(name = "recipes")
@ToString
public class Recipe extends AuditableAbstractAggregateRoot<Recipe> {

    // 👤 Usuario creador (Value Object)
    @Embedded
    @AttributeOverride(name = "userId", column = @Column(name = "user_id", nullable = false))
    private UserId userId;

    @Getter
    @Column(name = "name", nullable = false)
    private String name;

    @Getter
    @Column(name = "description")
    private String description;

    @Getter
    @Column(name = "preparation_time")
    private int preparationTime;

    @Getter
    @Column(name = "difficulty")
    private String difficulty;

    // 🔗 Relaciones con entidades
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "recipe_type_id", nullable = false)
    private RecipeType recipeType;

    // -----------------------------------------------------------
    // Constructor vacío requerido por JPA
    public Recipe() {
        this.userId = new UserId(); // Valor por defecto (0L)
    }

    // Constructor principal usado desde CommandService
    public Recipe(Long userId, String name, String description, int preparationTime,
                  String difficulty, Category category, RecipeType recipeType) {
        this.userId = new UserId(userId);
        this.name = name;
        this.description = description;
        this.preparationTime = preparationTime;
        this.difficulty = difficulty;
        this.category = category;
        this.recipeType = recipeType;
    }

    public void updateRecipe(String name, String description, int preparationTime, String difficulty, Category category, RecipeType recipeType) {
        this.name = name;
        this.description = description;
        this.preparationTime = preparationTime;
        this.difficulty = difficulty;
        this.category = category;
        this.recipeType = recipeType;
    }

    // -----------------------------------------------------------
    // Getter explícito para el Value Object
    public Long getUserId() {
        return this.userId.userId();
    }
}

