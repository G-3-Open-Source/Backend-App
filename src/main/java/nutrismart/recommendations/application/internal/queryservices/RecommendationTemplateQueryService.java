package nutrismart.recommendations.application.internal.queryservices;

import nutrismart.recommendations.domain.model.entities.RecommendationTemplate;
import nutrismart.recommendations.infrastructure.persistence.jpa.repositories.RecommendationTemplateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationTemplateQueryService {

    private final RecommendationTemplateRepository repository;

    public RecommendationTemplateQueryService(RecommendationTemplateRepository repository) {
        this.repository = repository;
    }

    public List<RecommendationTemplate> getAllTemplates() {
        return repository.findAll();
    }
}
