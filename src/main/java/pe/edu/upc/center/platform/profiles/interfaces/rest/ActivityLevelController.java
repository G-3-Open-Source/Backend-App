package pe.edu.upc.center.platform.profiles.interfaces.rest;

import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.platform.profiles.domain.model.Entities.ActivityLevel;
import pe.edu.upc.center.platform.profiles.infrastructure.persistence.jpa.repositories.ActivityLevelRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/activity-levels")
public class ActivityLevelController {
    private final ActivityLevelRepository repository;

    public ActivityLevelController(ActivityLevelRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<ActivityLevel> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public ActivityLevel create(@RequestBody ActivityLevel level) {
        level.setId(null);

        return repository.save(level);
    }
}
