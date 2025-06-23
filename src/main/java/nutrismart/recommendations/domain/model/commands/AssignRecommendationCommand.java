package nutrismart.recommendations.domain.model.commands;

import nutrismart.recommendations.domain.model.valueobjects.RecommendationStatus;
import nutrismart.recommendations.domain.model.valueobjects.TimeOfDay;

public record AssignRecommendationCommand(
        Long userId,
        Long templateId,
        String reason,
        String notes,
        TimeOfDay timeOfDay,
        Double score,
        RecommendationStatus status
) {}
