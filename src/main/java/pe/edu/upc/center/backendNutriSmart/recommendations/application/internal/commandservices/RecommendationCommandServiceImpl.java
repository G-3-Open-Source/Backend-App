package pe.edu.upc.center.backendNutriSmart.recommendations.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.backendNutriSmart.recommendations.domain.model.aggregates.Recommendation;
import pe.edu.upc.center.backendNutriSmart.recommendations.domain.model.commands.AssignRecommendationCommand;
import pe.edu.upc.center.backendNutriSmart.recommendations.domain.model.commands.DeleteRecommendationCommand;
import pe.edu.upc.center.backendNutriSmart.recommendations.domain.model.valueobjects.UserId;
import pe.edu.upc.center.backendNutriSmart.recommendations.domain.services.RecommendationCommandService;
import pe.edu.upc.center.backendNutriSmart.recommendations.infrastructure.persistence.jpa.repositories.RecommendationRepository;

@Service
public class RecommendationCommandServiceImpl implements RecommendationCommandService {

    private final RecommendationRepository recommendationRepository;

    public RecommendationCommandServiceImpl(RecommendationRepository recommendationRepository) {
        this.recommendationRepository = recommendationRepository;
    }

    @Override
    public int handle(AssignRecommendationCommand command) {
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
