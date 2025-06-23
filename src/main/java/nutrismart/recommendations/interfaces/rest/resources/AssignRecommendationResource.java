package nutrismart.recommendations.interfaces.rest.resources;

import nutrismart.recommendations.domain.model.valueobjects.RecommendationStatus;
import nutrismart.recommendations.domain.model.valueobjects.TimeOfDay;

public record AssignRecommendationResource(
        Long userId,
        Long templateId,
        String reason,
        String notes,
        TimeOfDay timeOfDay,
        Double score,
        RecommendationStatus status
) {}
