package pe.edu.upc.center.backendNutriSmart.recommendations.domain.services;

import pe.edu.upc.center.backendNutriSmart.recommendations.domain.model.aggregates.Recommendation;
import pe.edu.upc.center.backendNutriSmart.recommendations.domain.model.commands.AssignRecommendationCommand;
import pe.edu.upc.center.backendNutriSmart.recommendations.domain.model.commands.DeleteRecommendationCommand;

import java.util.Optional;

public interface RecommendationCommandService
{
    int handle(AssignRecommendationCommand command);
    void handle(DeleteRecommendationCommand command);
}
