package pe.edu.upc.center.backendNutriSmart.mealplan.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.center.backendNutriSmart.mealplan.domain.model.entities.MealPlanEntry;

@Repository
public interface MealPlanEntryRepository extends JpaRepository<MealPlanEntry, Integer> {
}
