package nutrismart.recommendations.interfaces.rest.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import nutrismart.recommendations.domain.model.entities.RecommendationTemplate;
import nutrismart.recommendations.infrastructure.persistence.jpa.repositories.RecommendationTemplateRepository;
import nutrismart.recommendations.interfaces.rest.resources.CreateTemplateResource;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/recommendation-templates")
@RequiredArgsConstructor
public class RecommendationTemplateController {

    private final RecommendationTemplateRepository repository;

    @GetMapping
    public ResponseEntity<List<RecommendationTemplate>> getAllTemplates() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<RecommendationTemplate> createTemplate(@RequestBody CreateTemplateResource resource) {
        RecommendationTemplate template = new RecommendationTemplate(resource.title(), resource.content());
        RecommendationTemplate saved = repository.save(template);
        return ResponseEntity.created(URI.create("/api/v1/recommendation-templates/" + saved.getId())).body(saved);
    }
}
