package nutrismart.recommendations.interfaces.rest.resources;

import nutrismart.recommendations.domain.model.valueobjects.RecommendationStatus;
import nutrismart.recommendations.domain.model.valueobjects.TimeOfDay;

import java.time.LocalDateTime;

public record RecommendationResource(
        Long id,
        Long userId,
        Long templateId,
        String reason,
        String notes,
        TimeOfDay timeOfDay,
        Double score,
        RecommendationStatus status,
        LocalDateTime assignedAt
) {}
