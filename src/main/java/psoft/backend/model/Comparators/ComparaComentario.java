package psoft.backend.model.Comparators;

import psoft.backend.model.Disciplina;
import psoft.backend.model.Perfil;


import java.util.Comparator;

public class ComparaComentario implements Comparator<Perfil> {
    @Override
    public int compare(Perfil p1, Perfil p2) {
        if (p1.getQtdComentario() == p2.getQtdComentario()){
            return p1.getDisciplina().compareTo(p2.getDisciplina());
        }
        return p2.getQtdComentario() - p1.getQtdComentario();
    }
}
