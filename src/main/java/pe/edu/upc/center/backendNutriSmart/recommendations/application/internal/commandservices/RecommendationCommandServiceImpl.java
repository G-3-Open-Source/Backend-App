package pe.edu.upc.center.backendNutriSmart.recommendations.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.backendNutriSmart.profiles.interfaces.acl.UserProfilesContextFacade;
import pe.edu.upc.center.backendNutriSmart.recommendations.domain.model.aggregates.Recommendation;
import pe.edu.upc.center.backendNutriSmart.recommendations.domain.model.commands.AssignRecommendationCommand;
import pe.edu.upc.center.backendNutriSmart.recommendations.domain.model.commands.AutoAssignRecommendationsCommand;
import pe.edu.upc.center.backendNutriSmart.recommendations.domain.model.commands.CreateRecommendationCommand;
import pe.edu.upc.center.backendNutriSmart.recommendations.domain.model.commands.DeleteRecommendationCommand;
import pe.edu.upc.center.backendNutriSmart.recommendations.domain.model.entities.RecommendationTemplate;
import pe.edu.upc.center.backendNutriSmart.recommendations.domain.model.valueobjects.UserId;
import pe.edu.upc.center.backendNutriSmart.recommendations.domain.services.RecommendationCommandService;
import pe.edu.upc.center.backendNutriSmart.recommendations.domain.services.RecommendationTemplateService;
import pe.edu.upc.center.backendNutriSmart.recommendations.infrastructure.persistence.jpa.repositories.RecommendationRepository;
import pe.edu.upc.center.backendNutriSmart.recommendations.domain.model.valueobjects.RecommendationStatus;
import pe.edu.upc.center.backendNutriSmart.recommendations.interfaces.rest.resources.UpdateRecommendationResource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecommendationCommandServiceImpl implements RecommendationCommandService {

    private final RecommendationRepository recommendationRepository;
    private final RecommendationTemplateService templateService;
    private final UserProfilesContextFacade userProfilesACL;

    public RecommendationCommandServiceImpl(
            RecommendationRepository recommendationRepository,
            RecommendationTemplateService templateService,
            UserProfilesContextFacade userProfilesACL) {
        this.recommendationRepository = recommendationRepository;
        this.templateService = templateService;
        this.userProfilesACL = userProfilesACL;
    }

    @Override
    public int handle(CreateRecommendationCommand command) {
        Optional<RecommendationTemplate> templateOpt = templateService.findById(command.templateId());
        if (templateOpt.isEmpty()) {
            throw new RuntimeException("Template not found: " + command.templateId());
        }

        Recommendation baseRecommendation = new Recommendation(
                templateOpt.get(),
                command.reason(),
                command.notes(),
                command.timeOfDay(),
                command.score(),
                command.status()
        );

        Recommendation savedRecommendation = recommendationRepository.save(baseRecommendation);
        return Math.toIntExact(savedRecommendation.getId());
    }

    @Override
    public int handle(AssignRecommendationCommand command) {
        validateUserExists(command.userId());

        Optional<RecommendationTemplate> templateOpt = templateService.findById(command.templateId());
        if (templateOpt.isEmpty()) {
            throw new RuntimeException("Template not found: " + command.templateId());
        }

        // ✅ CREAR RECOMMENDATION ASIGNADA DIRECTAMENTE
        Recommendation assignedRecommendation = Recommendation.assignToUser(
                new UserId(command.userId()),
                command.templateId(),
                command.reason(),
                command.notes(),
                command.timeOfDay(),
                command.score(),
                command.status()
        );

        Recommendation saved = recommendationRepository.save(assignedRecommendation);
        return Math.toIntExact(saved.getId());
    }

    @Override
    public List<Recommendation> handleAutoAssign(AutoAssignRecommendationsCommand command) {
        // ✅ VALIDAR USUARIO EXISTE EN PROFILES BC
        validateUserExists(command.userId());

        // ✅ OBTENER RECOMMENDATIONS BASE DISPONIBLES
        List<Recommendation> baseRecommendations = recommendationRepository.findByUserIdIsNull();

        if (baseRecommendations.isEmpty()) {
            throw new RuntimeException("No hay recommendations BASE disponibles para auto-asignar");
        }

        // ✅ CREAR COPIAS ASIGNADAS AL USUARIO (límite de 3)
        List<Recommendation> assignedRecommendations = baseRecommendations.stream()
                .limit(3)
                .map(base -> createAssignedCopy(base, command.userId()))
                .collect(Collectors.toList());

        return recommendationRepository.saveAll(assignedRecommendations);
    }

    @Override
    public void handle(DeleteRecommendationCommand command) {
        // ✅ VALIDAR RECOMMENDATION EXISTE
        Recommendation recommendation = recommendationRepository.findById(command.recommendationId())
                .orElseThrow(() -> new RuntimeException("Recommendation not found: " + command.recommendationId()));

        // ✅ ELIMINAR RECOMMENDATION
        recommendationRepository.delete(recommendation);
    }

    // ✅ MÉTODO PRIVADO PARA VALIDACIÓN ACL
    private void validateUserExists(Long userId) {
        userProfilesACL.fetchById(userId)
                .orElseThrow(() -> new RuntimeException("User profile not found: " + userId));
    }

    // ✅ MÉTODO PRIVADO PARA CREAR COPIA ASIGNADA
    private Recommendation createAssignedCopy(Recommendation base, Long userId) {
        return Recommendation.assignToUser(
                new UserId(userId),
                base.getTemplate().getId(),
                base.getReason(),
                base.getNotes(),
                base.getTimeOfDay(),
                base.getScore(),
                RecommendationStatus.ACTIVE
        );
    }
    @Override
    public Recommendation handleUpdate(Long recommendationId, UpdateRecommendationResource resource) {
        Recommendation recommendation = recommendationRepository.findById(recommendationId)
                .orElseThrow(() -> new RuntimeException("Recommendation not found: " + recommendationId));

        // Actualiza solo los campos permitidos
        if (resource.reason() != null) recommendation.setReason(resource.reason());
        if (resource.notes() != null) recommendation.setNotes(resource.notes());
        if (resource.timeOfDay() != null) recommendation.setTimeOfDay(resource.timeOfDay());
        if (resource.score() != null) recommendation.setScore(resource.score());
        if (resource.status() != null) recommendation.setStatus(resource.status());

        return recommendationRepository.save(recommendation);
    }
}