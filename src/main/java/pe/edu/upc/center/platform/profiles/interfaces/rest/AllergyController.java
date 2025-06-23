package pe.edu.upc.center.platform.profiles.interfaces.rest;

import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.platform.profiles.domain.model.Entities.Allergy;
import pe.edu.upc.center.platform.profiles.infrastructure.persistence.jpa.repositories.AllergyRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/allergies")
public class AllergyController {
    private final AllergyRepository repository;

    public AllergyController(AllergyRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Allergy> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public Allergy create(@RequestBody Allergy allergy) {
        return repository.save(allergy);
    }
}
