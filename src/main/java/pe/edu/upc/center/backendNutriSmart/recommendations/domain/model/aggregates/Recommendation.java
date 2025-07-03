package pe.edu.upc.center.backendNutriSmart.recommendations.domain.model.aggregates;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.upc.center.backendNutriSmart.recommendations.domain.model.entities.RecommendationTemplate;
import pe.edu.upc.center.backendNutriSmart.recommendations.domain.model.valueobjects.RecommendationStatus;
import pe.edu.upc.center.backendNutriSmart.recommendations.domain.model.valueobjects.TimeOfDay;
import pe.edu.upc.center.backendNutriSmart.recommendations.domain.model.valueobjects.UserId;
import pe.edu.upc.center.backendNutriSmart.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.time.LocalDateTime;

@Entity
@Table(name = "recommendations")
@NoArgsConstructor
public class Recommendation extends AuditableAbstractAggregateRoot<Recommendation> {

    @Getter
    @Embedded
    private UserId userId;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "template_id", nullable = false)
    @JsonBackReference
    private RecommendationTemplate template;

    @Setter
    @Getter
    @Column(nullable = false)
    private String reason;

    @Setter
    @Getter
    @Column(nullable = false, columnDefinition = "TEXT")
    private String notes;

    @Setter
    @Getter
    @Enumerated(EnumType.STRING)
    @Column(name = "time_of_day", nullable = false)
    private TimeOfDay timeOfDay;

    @Setter
    @Getter
    @Column(nullable = false)
    private Double score;

    @Setter
    @Getter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecommendationStatus status;

    @Getter
    @Column(name = "assigned_at", nullable = false)
    private LocalDateTime assignedAt;

    // ------------------ Constructor privado ------------------
    private Recommendation(UserId userId, RecommendationTemplate template, String reason, String notes,
                           TimeOfDay timeOfDay, Double score, RecommendationStatus status) {
        this.userId = userId;
        this.template = template;
        this.reason = reason;
        this.notes = notes;
        this.timeOfDay = timeOfDay;
        this.score = score;
        this.status = status;
        this.assignedAt = LocalDateTime.now();
    }

    // ------------------ Método de creación ------------------
    public static Recommendation assignToUser(UserId userId, Long templateId, String reason, String notes,
                                              TimeOfDay timeOfDay, Double score, RecommendationStatus status) {
        RecommendationTemplate template = new RecommendationTemplate();
        template.setId(templateId);
        return new Recommendation(userId, template, reason, notes, timeOfDay, score, status);
    }

    // ------------------ Lógica de desactivación ------------------
    public void deactivate() {
        this.status = RecommendationStatus.INACTIVE;
    }

    public Long getTemplateId() {
        return template != null ? template.getId() : null;
    }

}
