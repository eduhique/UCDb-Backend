package psoft.backend.model.Comparators;

import psoft.backend.model.Disciplina;


import java.util.Comparator;

public class ComparaComentario implements Comparator<Disciplina> {
    @Override
    public int compare(Disciplina p1, Disciplina p2) {
        if (p1.getPerfil().getComentarios().size() == p2.getPerfil().getComentarios().size()){
            return p1.getNome().compareTo(p2.getNome());
        }
        return p2.getPerfil().getComentarios().size() - p1.getPerfil().getComentarios().size();
    }
}
