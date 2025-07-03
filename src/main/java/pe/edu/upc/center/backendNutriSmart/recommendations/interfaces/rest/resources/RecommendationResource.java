package pe.edu.upc.center.backendNutriSmart.recommendations.interfaces.rest.resources;

import pe.edu.upc.center.backendNutriSmart.recommendations.domain.model.valueobjects.RecommendationStatus;
import pe.edu.upc.center.backendNutriSmart.recommendations.domain.model.valueobjects.TimeOfDay;

import java.time.LocalDateTime;

public record RecommendationResource(
        int id,
        Long userId,
        Long templateId,
        String reason,
        String notes,
        TimeOfDay timeOfDay,
        Double score,
        RecommendationStatus status,
        LocalDateTime assignedAt
) {}
