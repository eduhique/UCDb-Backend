package psoft.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JsonBackReference(value = "perfil")
    private Perfil perfil;

    @OneToOne
    private User user;

    private double nota;

    public Nota(Perfil perfil, User user, double nota) {
        this.perfil = perfil;
        this.user = user;
        this.nota = nota;
    }
}
