package nutrismart.recommendations.application.internal.queryservices;

import org.springframework.stereotype.Service;
import nutrismart.recommendations.domain.model.aggregates.Recommendation;
import nutrismart.recommendations.domain.model.queries.GetRecommendationsByUserQuery;
import nutrismart.recommendations.domain.services.RecommendationQueryService;
import nutrismart.recommendations.infrastructure.persistence.jpa.repositories.RecommendationRepository;

import java.util.List;

@Service
public class RecommendationQueryServiceImpl implements RecommendationQueryService {

    private final RecommendationRepository recommendationRepository;

    public RecommendationQueryServiceImpl(RecommendationRepository recommendationRepository) {
        this.recommendationRepository = recommendationRepository;
    }

    @Override
    public List<Recommendation> handle(GetRecommendationsByUserQuery query) {
        return this.recommendationRepository.findAllByUserId_Value(query.userId());
    }
}
