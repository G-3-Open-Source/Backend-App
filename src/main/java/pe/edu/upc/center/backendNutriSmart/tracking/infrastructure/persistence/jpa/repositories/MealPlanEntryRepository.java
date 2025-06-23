package pe.edu.upc.center.backendNutriSmart.tracking.infrastructure.persistence.jpa.repositories;

import pe.edu.upc.center.backendNutriSmart.tracking.domain.model.Entities.MealPlanEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MealPlanEntryRepository extends JpaRepository<MealPlanEntry, Long> {

    List<MealPlanEntry> findAllByTrackingId(Long trackingId);
}