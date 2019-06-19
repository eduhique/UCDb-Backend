package psoft.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Data
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JsonBackReference(value = "perfil")
    private Perfil perfil;

    @OneToOne
    private User user;

    private String text;

    private String tempo;

    @OneToMany
    @JsonBackReference(value = "comentario")
    private List<Comentario> comentarios;

    public Comentario() {
    }

    public Comentario(Perfil perfil, User user, String text, String tempo, List<Comentario> comentarios) {
        this.perfil = perfil;
        this.user = user;
        this.text = text;
        this.tempo = tempo;
        this.comentarios = comentarios;
    }
}
