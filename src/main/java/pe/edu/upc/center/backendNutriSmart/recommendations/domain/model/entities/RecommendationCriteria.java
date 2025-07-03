package pe.edu.upc.center.backendNutriSmart.recommendations.domain.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pe.edu.upc.center.backendNutriSmart.recommendations.domain.model.valueobjects.TimeOfDay;

@Entity
@Table(name = "recommendation_criteria")
@NoArgsConstructor
public class RecommendationCriteria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    private String objective;

    @Getter
    private String allergy;

    @Getter
    private String activityLevel;

    @Getter
    @Enumerated(EnumType.STRING)
    private TimeOfDay preferredTime;

    public RecommendationCriteria(String objective, String allergy, String activityLevel, TimeOfDay preferredTime) {
        this.objective = objective;
        this.allergy = allergy;
        this.activityLevel = activityLevel;
        this.preferredTime = preferredTime;
    }
}
