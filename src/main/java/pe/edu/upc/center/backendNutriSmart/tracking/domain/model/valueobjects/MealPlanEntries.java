package pe.edu.upc.center.backendNutriSmart.tracking.domain.model.valueobjects;


import pe.edu.upc.center.backendNutriSmart.tracking.domain.model.Entities.MealPlanEntry;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ToString
@Embeddable
public class MealPlanEntries {

  @Getter
  @OneToMany(mappedBy = "tracking", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<MealPlanEntry> mealPlanEntries = new ArrayList<>();

  public MealPlanEntries() {
    this.mealPlanEntries = new ArrayList<>();
  }

  // Todos tus métodos existentes se quedan igual
  public void addEntry(MealPlanEntry entry) {
    this.mealPlanEntries.add(entry);
  }

  public void addEntries(List<MealPlanEntry> entries) {
    this.mealPlanEntries.addAll(entries);
  }

  public boolean removeEntryById(Long entryId) {
    return mealPlanEntries.removeIf(e -> e.getId().equals(entryId));
  }
  public void clearEntries() {
    this.mealPlanEntries.clear();
  }
}
