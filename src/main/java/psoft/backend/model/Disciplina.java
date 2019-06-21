package psoft.backend.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@ApiModel(value = "Disciplina", description = "Modelo de uma disciplina. Esse modelo representa a entidade comentário" +
        " no banco de dados e possui as funções básicas de getters e setters para seus atributos.")
@Data
@Entity
public class Disciplina {

    @ApiModelProperty(value = "Identificador único da disciplina.", example = "1", position = 0)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ApiModelProperty(value = "Nome da disciplina.", example = "PROJETO DE SOFTWARE", position = 1)
    private String nome;

    @ApiModelProperty(value = "referencia a classe perfil com a qual possui um ligação", hidden = true)
    @OneToOne
    @JsonBackReference(value = "perfil")
    private Perfil perfil;

    public Disciplina() {
    }

    public Disciplina(String nome, Perfil perfil) {
        this.nome = nome;
        this.perfil = perfil;
    }

    @Override
    public String toString() {
        return id + " - " + nome;
    }
}
