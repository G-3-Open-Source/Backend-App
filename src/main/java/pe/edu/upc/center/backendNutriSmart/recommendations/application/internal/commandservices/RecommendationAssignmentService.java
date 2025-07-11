package pe.edu.upc.center.backendNutriSmart.recommendations.application.internal.commandservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.center.backendNutriSmart.recommendations.domain.model.aggregates.Recommendation;
import pe.edu.upc.center.backendNutriSmart.recommendations.domain.model.entities.RecommendationTemplate;
import pe.edu.upc.center.backendNutriSmart.recommendations.domain.model.valueobjects.RecommendationStatus;
import pe.edu.upc.center.backendNutriSmart.recommendations.domain.model.valueobjects.UserId;
import pe.edu.upc.center.backendNutriSmart.recommendations.domain.services.RecommendationTemplateService;
import pe.edu.upc.center.backendNutriSmart.recommendations.infrastructure.persistence.jpa.repositories.RecommendationRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationAssignmentService implements pe.edu.upc.center.backendNutriSmart.recommendations.domain.services.RecommendationAssignmentService {

    @Autowired
    private RecommendationTemplateService templateService;

    @Autowired
    private RecommendationRepository recommendationRepository;

    @Override
    public List<Recommendation> autoAssignRecommendations(Long userId) {
        List<RecommendationTemplate> allTemplates = templateService.getAllTemplates();

        if (allTemplates.isEmpty()) {
            return new ArrayList<>();
        }

        Random random = new Random(userId);
        RecommendationTemplate selectedTemplate = allTemplates.get(random.nextInt(allTemplates.size()));

        List<Recommendation> baseRecommendations = recommendationRepository
                .findByTemplateAndUserIdIsNull(selectedTemplate);

        if (baseRecommendations.isEmpty()) {
            return new ArrayList<>();
        }

        Collections.shuffle(baseRecommendations, random);
        int assignCount = Math.min(3, baseRecommendations.size());

        List<Recommendation> userRecommendations = baseRecommendations.stream()
                .limit(assignCount)
                .map(base -> createCopyForUser(base, userId))
                .collect(Collectors.toList());

        return recommendationRepository.saveAll(userRecommendations);
    }

    @Override
    public Recommendation assignSingleRecommendation(Long userId, Long templateId) {
        RecommendationTemplate template = templateService.findById(templateId)
                .orElseThrow(() -> new RuntimeException("Template not found"));

        Recommendation baseRecommendation = recommendationRepository
                .findFirstByTemplateAndUserIdIsNull(template)
                .orElseThrow(() -> new RuntimeException("No hay recommendations disponibles para este template"));

        Recommendation userRecommendation = createCopyForUser(baseRecommendation, userId);
        return recommendationRepository.save(userRecommendation);
    }

    private Recommendation createCopyForUser(Recommendation base, Long userId) {
        Recommendation copy = new Recommendation();
        copy.setUserId(new UserId(userId));
        copy.setTemplate(base.getTemplate());
        copy.setReason(base.getReason());
        copy.setNotes(base.getNotes());
        copy.setTimeOfDay(base.getTimeOfDay());
        copy.setScore(base.getScore());
        copy.setStatus(RecommendationStatus.ACTIVE);
        copy.setAssignedAt(LocalDateTime.now());
        return copy;
    }
}