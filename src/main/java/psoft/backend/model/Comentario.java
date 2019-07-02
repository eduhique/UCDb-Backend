package psoft.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


@ApiModel(value = "Comentário", description = "Modelo de um Comentário no banco de dados. Possui as funções básicas de" +
        " getters e setters para seus atributos. E funcões personalizadas para retornar outras informações a partir dos atributos existentes.")
@Entity
@Data
public class Comentario {

    @ApiModelProperty(value = "Identificador único do comentário.", example = "1", position = 0)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ApiModelProperty(value = "Perfil ao qual o comentário é ligado.", position = 5)
    @ManyToOne
    @JsonBackReference(value = "perfil")
    private Perfil perfil;

    @ApiModelProperty(value = "Usuário que fez o comentário.", position = 2)
    @OneToOne
    private User user;

    @ApiModelProperty(value = "Texto do comentário", example = "Essa disciplina é muito boa. Ótimos professores!!!", position = 1)
    private String text;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ApiModelProperty(value = "Hora em que foi feito o comentário", example = "2:00", position = 3)
    private String hora;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ApiModelProperty(value = "Data em que foi feito o comentário", example = "20/06/2019", position = 4)
    private String data;

    @ApiModelProperty(value = "Boleando que indica se um comentário foi apagado(true) ou não", example = "true", position = 7)
    private boolean apagado;

    @ApiModelProperty(value = "Lista de resposta que um comentário pode ter.", position = 6)
    @OneToMany
    private List<Comentario> respostas;

    public Comentario() {
    }

    public Comentario(Perfil perfil, User user, String text, String hora, String data, List<Comentario> comentarios) {
        this.perfil = perfil;
        this.user = user;
        this.text = text;
        this.hora = hora;
        this.data = data;
        this.respostas = comentarios;
    }

    public void addResposta(Comentario resposta) {
        respostas.add(resposta);
    }

    public String getText() {
        String result = "";
        if (!apagado) result = text;
        return result;
    }

    public List<Comentario> getRespostas() {
        List<Comentario> result = respostas;
        return result;
    }


    public int getQtdResposta() {
        int result = 0;
        if (!getRespostas().isEmpty() && !isApagado()) {
            result = qtdRecursivo(this);
        }

        return result;
    }

    private int qtdRecursivo(Comentario comentario) {
        int result = 0;
        if (!comentario.getRespostas().isEmpty()) {
            for (Comentario c : comentario.getRespostas()) {
                if (!c.isApagado())
                    result += 1 + qtdRecursivo(c);
            }
        }
        return result;
    }

    public void setApagado(boolean apagado) {
        this.apagado = apagado;
        if (!getRespostas().isEmpty()) {
            apagaRecursivo(getRespostas());
        }
    }

    private void apagaRecursivo(List<Comentario> respostas) {
        if (!respostas.isEmpty()) {
            for (Comentario r : respostas) {
                r.setApagado(true);
            }
        }
    }
}
