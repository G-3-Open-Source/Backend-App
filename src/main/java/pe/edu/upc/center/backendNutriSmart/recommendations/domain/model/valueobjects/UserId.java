package pe.edu.upc.center.backendNutriSmart.recommendations.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record UserId(Long value) {
    public UserId {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("Profile ID must be positive and non-null.");
        }
    }

    public UserId() {
        this(0L);
    }
}
