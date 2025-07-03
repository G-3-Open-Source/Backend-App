package pe.edu.upc.center.backendNutriSmart.recommendations.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.center.backendNutriSmart.recommendations.domain.model.aggregates.Recommendation;

import java.util.List;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    List<Recommendation> findAllByUserId_Value(Long userId);
}
