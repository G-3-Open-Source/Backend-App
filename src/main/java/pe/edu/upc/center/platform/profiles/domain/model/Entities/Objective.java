package pe.edu.upc.center.platform.profiles.domain.model.Entities;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "objectives")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Objective {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "objective_name", length = 100, nullable = false)
    private String objectiveName;

    @Column(name = "score", nullable = false)
    private int score;


    public Objective(String objectiveName, int score) {
        this.objectiveName = objectiveName;
        this.score = score;
    }

    //metodos
    public int calculateScore() {

        return this.score;
    }

    public String getDescription() {
        return String.format("Objective: %s, Score: %d", this.objectiveName, this.score);
    }
}