package nutrismart.recommendations.domain.services;

import nutrismart.recommendations.domain.model.aggregates.Recommendation;
import nutrismart.recommendations.domain.model.queries.GetRecommendationsByUserQuery;

import java.util.List;

public interface RecommendationQueryService {
    List<Recommendation> handle(GetRecommendationsByUserQuery query);
}
