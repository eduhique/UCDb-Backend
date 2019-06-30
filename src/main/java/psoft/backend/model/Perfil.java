package psoft.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Collections;
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

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Transient
    private User userAtual;

    public Perfil() {
    }


    public Perfil(Disciplina disciplina, List<Comentario> comentarios, List<User> curtidas) {
        this.disciplina = disciplina;
        this.comentarios = comentarios;
        this.curtidas = curtidas;
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

    public Boolean getCurtidaUser() {
        return curtidas.contains(userAtual);
    }

    public void setCurtidasUser(User user) {
        if (this.curtidas.contains(user)) this.curtidas.remove(user);
        else this.curtidas.add(user);
    }


    public List<Comentario> getComentarios() {
        List<Comentario> saida = comentarios;
        Collections.reverse(saida);
        return saida;
    }

    public User getUserAtual() {
        return userAtual;
    }

    public void setUserAtual(User userAtual) {
        this.userAtual = userAtual;
    }

    public void addComentario(Comentario comentario) {
        comentarios.add(comentario);
    }

    public int getQtdComentario(){
        int result = 0;
        if(!getComentarios().isEmpty()){
            result = qtdRecursivo(this);
        }
        return result;
    }

    private int qtdRecursivo(Perfil perfil) {
        int result = 0;
        if(!perfil.getComentarios().isEmpty()){
            for (Comentario c: perfil.getComentarios()) {
                if(!c.isApagado()) {
                    result += 1 + c.getQtdResposta();
                }
            }
        }
        return result;
    }
}
