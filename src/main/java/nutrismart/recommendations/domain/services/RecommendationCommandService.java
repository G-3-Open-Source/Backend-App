package nutrismart.recommendations.domain.services;

import nutrismart.recommendations.domain.model.aggregates.Recommendation;
import nutrismart.recommendations.domain.model.commands.AssignRecommendationCommand;
import nutrismart.recommendations.domain.model.commands.DeleteRecommendationCommand;

import java.util.Optional;

public interface RecommendationCommandService
{
    Long handle(AssignRecommendationCommand command);
    void handle(DeleteRecommendationCommand command);
}
