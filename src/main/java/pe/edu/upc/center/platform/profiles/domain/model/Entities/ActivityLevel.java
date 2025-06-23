package pe.edu.upc.center.platform.profiles.domain.model.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.center.platform.shared.domain.model.entities.AuditableModel;

@Getter
@Setter
@Entity
@Table(name = "activity_levels")
public class ActivityLevel extends AuditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @NotNull
    @Column(name = "description", nullable = false, length = 255)
    private String description;

    @NotNull
    @Column(name = "activity_factor", nullable = false)
    private double activityFactor;

    public ActivityLevel() {
    }

    public ActivityLevel(String name, String description, double activityFactor) {
        this.name = name;
        this.description = description;
        this.activityFactor = activityFactor;
    }

    public double calculateCalories(double weight, double height, int age) {
        // Ejemplo de cálculo usando fórmula de Harris-Benedict ajustada por factor de actividad
        double bmr = 10 * weight + 6.25 * height - 5 * age + 5; // Suponiendo hombre
        return bmr * this.activityFactor;
    }

    public String describeLevel() {
        return this.name + ": " + this.description;
    }

    public void updateLevel(String name, String description, double activityFactor) {
        this.name = name;
        this.description = description;
        this.activityFactor = activityFactor;
    }
}
