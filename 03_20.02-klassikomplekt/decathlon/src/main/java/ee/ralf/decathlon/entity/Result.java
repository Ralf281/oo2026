package ee.ralf.decathlon.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@Entity
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sport;   // 100m või kaugushüpe
    private double result;  // 12.5 või 8.0
    private int points;     // arvutatakse backendis

    @ManyToOne
    @JoinColumn(name = "athlete_id", nullable = false)
    @JsonIgnore
    private Athlete athlete;
}