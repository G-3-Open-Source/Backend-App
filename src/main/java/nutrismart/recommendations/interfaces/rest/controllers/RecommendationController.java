package nutrismart.recommendations.interfaces.rest.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import nutrismart.recommendations.domain.model.aggregates.Recommendation;
import nutrismart.recommendations.domain.services.RecommendationCommandService;
import nutrismart.recommendations.domain.services.RecommendationQueryService;
import nutrismart.recommendations.infrastructure.persistence.jpa.repositories.RecommendationRepository;
import nutrismart.recommendations.domain.model.commands.AssignRecommendationCommand;
import nutrismart.recommendations.domain.model.commands.DeleteRecommendationCommand;
import nutrismart.recommendations.domain.model.queries.GetRecommendationsByUserQuery;
import nutrismart.recommendations.interfaces.rest.resources.AssignRecommendationResource;
import nutrismart.recommendations.interfaces.rest.resources.RecommendationResource;
import nutrismart.recommendations.interfaces.rest.resources.UpdateRecommendationResource;
import nutrismart.recommendations.interfaces.rest.transform.AssignRecommendationCommandFromResourceAssembler;
import nutrismart.recommendations.interfaces.rest.transform.RecommendationResourceFromEntityAssembler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/recommendations", produces = "application/json")
@Tag(name = "Recommendations", description = "Recommendation Management Endpoints")
public class RecommendationController {

    private final RecommendationCommandService commandService;
    private final RecommendationQueryService queryService;
    private final RecommendationRepository recommendationRepository;

    public RecommendationController(
            RecommendationCommandService commandService,
            RecommendationQueryService queryService,
            RecommendationRepository recommendationRepository) {
        this.commandService = commandService;
        this.queryService = queryService;
        this.recommendationRepository = recommendationRepository;
    }

    @PostMapping
    public ResponseEntity<RecommendationResource> assignRecommendation(@RequestBody AssignRecommendationResource resource) {
        AssignRecommendationCommand command = AssignRecommendationCommandFromResourceAssembler.toCommandFromResource(resource);
        Long recommendationId = commandService.handle(command);
        GetRecommendationsByUserQuery query = new GetRecommendationsByUserQuery(command.userId());

        return queryService.handle(query).stream()
                .filter(r -> r.getId().equals(recommendationId))
                .findFirst()
                .map(value -> new ResponseEntity<>(
                        RecommendationResourceFromEntityAssembler.toResourceFromEntity(value),
                        HttpStatus.CREATED))
                .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{recommendationId}")
    public ResponseEntity<Void> deleteRecommendation(@PathVariable Long recommendationId) {
        commandService.handle(new DeleteRecommendationCommand(recommendationId));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RecommendationResource>> getRecommendationsByUser(@PathVariable Long userId) {
        List<Recommendation> recommendations = queryService.handle(new GetRecommendationsByUserQuery(userId));
        List<RecommendationResource> resources = recommendations.stream()
                .map(RecommendationResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recommendation> updateRecommendation(
            @PathVariable Long id,
            @RequestBody UpdateRecommendationResource resource) {

        return recommendationRepository.findById(id).map(existing -> {
            existing.setReason(resource.reason());
            existing.setNotes(resource.notes());
            existing.setTimeOfDay(resource.timeOfDay());
            existing.setScore(resource.score());
            existing.setStatus(resource.status());
            Recommendation updated = recommendationRepository.save(existing);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }
}
