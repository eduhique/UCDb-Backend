package psoft.backend.model.Comparators;

import psoft.backend.model.Disciplina;

import java.util.Comparator;

public class ComparaLike implements Comparator<Disciplina> {

    @Override
    public int compare(Disciplina p1, Disciplina p2) {
        if (p1.getPerfil().getCurtidas() == p2.getPerfil().getCurtidas()){
            return p1.getNome().compareTo(p2.getNome());
        }
        return p2.getPerfil().getCurtidas() - p1.getPerfil().getCurtidas();
    }
}
