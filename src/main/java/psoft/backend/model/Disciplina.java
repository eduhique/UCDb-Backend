package psoft.backend.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nome;

    public Disciplina() {
    }

    public Disciplina(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return id + " - " + nome;
    }
}
