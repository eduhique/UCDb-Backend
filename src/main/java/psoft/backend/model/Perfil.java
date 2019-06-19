package psoft.backend.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private Disciplina disciplina;

    @OneToMany
    private List<Comentario> comentarios;

    @ManyToMany
    @JoinTable(name = "curtidas", joinColumns = {@JoinColumn(name = "perfil_id")}, inverseJoinColumns = {@JoinColumn(name = "user_email")})
    private List<User> curtidas;

    @ManyToMany
    private List<Nota> notas;

    @Transient
    private User userAtual;

    public Perfil() {
    }


    public Perfil(Disciplina disciplina, List<Comentario> comentarios, List<User> curtidas, List<Nota> notas) {
        this.disciplina = disciplina;
        this.comentarios = comentarios;
        this.curtidas = curtidas;
        this.notas = notas;
    }

    public long getId() {
        return id;
    }
    public String getDisciplina() {
        return disciplina.getNome();
    }

    public int getCurtidas() {
        return curtidas.size();
    }

    public double getNota() {
        double soma = 0.0;
        double notaFinal = 0.0;
        int users = notas.size();
        if (!notas.isEmpty()) {
            for (Nota n : notas) {
                soma += n.getNota();
                users++;
                notaFinal = soma / users;
            }
        }
        return notaFinal;
    }
    public double getNotaUser(){
        double result = 0;
        if(notas.contains(userAtual)){
            result = notas.get(notas.indexOf(userAtual)).getNota();
        }
        return result;
    }


    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public Boolean getCurtidaUser() {
        return curtidas.contains(userAtual);
    }
    public void setUserAtual(User userAtual) {
        this.userAtual = userAtual;
    }
}
