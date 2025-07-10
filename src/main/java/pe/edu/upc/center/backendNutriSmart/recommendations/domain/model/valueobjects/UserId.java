package pe.edu.upc.center.backendNutriSmart.recommendations.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record UserId(Long value) {
    public UserId {
        // Permitir null para recommendations sin asignar
        if (value != null && value <= 0) {
            throw new IllegalArgumentException("User ID must be positive when provided.");
        }
    }

    // Constructor sin argumentos requerido por JPA
    public UserId() {
        this(null); // Permitir null para recommendations sin asignar
    }

    // Método requerido por el assembler
    public Long getValue() {
        return value;
    }
}