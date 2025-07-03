package pe.edu.upc.center.backendNutriSmart.recommendations.infrastructure.persistence.jpa.repositories;

import pe.edu.upc.center.backendNutriSmart.recommendations.domain.model.entities.RecommendationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendationTemplateRepository extends JpaRepository<RecommendationTemplate, Long> {
    // Puedes agregar métodos personalizados si los necesitas, por ejemplo:
    // Optional<RecommendationTemplate> findByTitle(String title);
}
