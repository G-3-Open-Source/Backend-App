package pe.edu.upc.center.backendNutriSmart.recommendations.interfaces.rest.transform;

import pe.edu.upc.center.backendNutriSmart.recommendations.domain.model.aggregates.Recommendation;
import pe.edu.upc.center.backendNutriSmart.recommendations.interfaces.rest.resources.RecommendationResource;

public class RecommendationResourceFromEntityAssembler {

    public static RecommendationResource toResourceFromEntity(Recommendation entity) {
        return new RecommendationResource(
                entity.getId(),
                entity.getUserId().value(),
                entity.getTemplateId(),
                entity.getReason(),
                entity.getNotes(),
                entity.getTimeOfDay(),
                entity.getScore(),
                entity.getStatus(),
                entity.getAssignedAt()
        );
    }
}
