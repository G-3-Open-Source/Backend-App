package pe.edu.upc.center.backendNutriSmart.recommendations.interfaces.rest.transform;

import pe.edu.upc.center.backendNutriSmart.recommendations.domain.model.aggregates.Recommendation;
import pe.edu.upc.center.backendNutriSmart.recommendations.interfaces.rest.resources.RecommendationResource;

public class RecommendationResourceFromEntityAssembler {
    public static RecommendationResource toResourceFromEntity(Recommendation entity) {
        return new RecommendationResource(
                (long) entity.getId(),
                entity.getUserId() != null ? entity.getUserId().getValue() : null, // ← Manejar null
                entity.getTemplateId(),
                entity.getReason(),
                entity.getNotes(),
                entity.getTimeOfDay().name(),
                entity.getScore(),
                entity.getStatus().name(),
                entity.getAssignedAt() // Puede ser null para recommendations sin asignar
        );
    }
}