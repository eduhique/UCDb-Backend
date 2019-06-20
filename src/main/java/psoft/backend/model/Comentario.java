package psoft.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    private String hora;
    private String data;

    @OneToMany
    private List<Comentario> comentarios;

    public Comentario() {
    }

    public Comentario(Perfil perfil, User user, String text, String hora, String data, List<Comentario> comentarios) {
        this.perfil = perfil;
        this.user = user;
        this.text = text;
        this.hora = hora;
        this.data = data;
        this.comentarios = comentarios;
    }

    public void addComentario(Comentario resposta) {
        comentarios.add(resposta);
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }
}
