package psoft.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.List;

@ApiModel(value = "Perfil", description = "Modelo do perfil de uma disciplina. Nesse modelos é possivel criar um perfil " +
        " adicionar um comentário, uma nota e um curtida")
@Entity
public class Perfil {

    @ApiModelProperty(value = "Identificador único do perfil.", example = "1", position = 0)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ApiModelProperty(value = "Disciplina ao Qual o perfil está associado.", example = "PROJETO DE SOFTWARE", position = 1)
    @OneToOne
    private Disciplina disciplina;

    @ApiModelProperty(value = "Comentários feitos no Perfil de uma disciplina.", position = 2)
    @OneToMany
    private List<Comentario> comentarios;

    @ApiModelProperty(value = "Lista dos usuários que curtiram o perfil da disciplina.", position = 3)
    @ManyToMany
    @JoinTable(name = "curtidas", joinColumns = {@JoinColumn(name = "perfil_id")}, inverseJoinColumns = {@JoinColumn(name = "user_email")})
    private List<User> curtidas;

    @ApiModelProperty(value = "Notas que os usuários deram a disciplina.", position = 4)
    @ManyToMany
    private List<Nota> notas;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
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

    public void addComentario(Comentario comentario){
        comentarios.add(comentario);
    }
}
