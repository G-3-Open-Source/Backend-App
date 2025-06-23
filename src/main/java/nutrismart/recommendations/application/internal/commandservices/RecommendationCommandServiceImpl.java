package nutrismart.recommendations.application.internal.commandservices;

import org.springframework.stereotype.Service;
import nutrismart.recommendations.domain.model.aggregates.Recommendation;
import nutrismart.recommendations.domain.model.commands.AssignRecommendationCommand;
import nutrismart.recommendations.domain.model.commands.DeleteRecommendationCommand;
import nutrismart.recommendations.domain.model.valueobjects.UserId;
import nutrismart.recommendations.domain.services.RecommendationCommandService;
import nutrismart.recommendations.infrastructure.persistence.jpa.repositories.RecommendationRepository;

@Service
public class RecommendationCommandServiceImpl implements RecommendationCommandService {

    private final RecommendationRepository recommendationRepository;

    public RecommendationCommandServiceImpl(RecommendationRepository recommendationRepository) {
        this.recommendationRepository = recommendationRepository;
    }

    @Override
    public Long handle(AssignRecommendationCommand command) {
        var recommendation = Recommendation.assignToUser(
                new UserId(command.userId()),
                command.templateId(),
                command.reason(),
                command.notes(),
                command.timeOfDay(),
                command.score(),
                command.status()
        );

        recommendationRepository.save(recommendation);
        return recommendation.getId();
    }

    @Override
    public void handle(DeleteRecommendationCommand command) {
        var recommendation = recommendationRepository.findById(command.recommendationId())
                .orElseThrow(() -> new IllegalArgumentException("Recommendation not found"));

        recommendation.deactivate(); // cambia el estado a INACTIVE
        recommendationRepository.save(recommendation); // guarda el cambio
    }
}
