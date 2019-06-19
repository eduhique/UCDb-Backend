package psoft.backend.service;

import org.springframework.stereotype.Service;
import psoft.backend.dao.PerfilDAO;
import psoft.backend.model.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class PerfilService {

    private final PerfilDAO perfilDAO;

    private final DisciplinaService disciplinaService;

    public PerfilService(PerfilDAO perfilDAO, DisciplinaService disciplinaService) {
        this.perfilDAO = perfilDAO;
        this.disciplinaService = disciplinaService;
    }

    public List<Perfil> createPerfilAll(List<Disciplina> disciplina){
        List<Perfil> perfils = new ArrayList<Perfil>();
        for (Disciplina d: disciplina) {
            Perfil p = new Perfil(d, new ArrayList<Comentario>(), new ArrayList<User>(), new ArrayList<Nota>());
            perfils.add(p);
            disciplinaService.findById(d.getId()).setPerfil(p);
        }

        return perfilDAO.saveAll(perfils);
    }

    public Perfil findById(long id) {
        return perfilDAO.findById(id);
    }

    public List<Perfil> findAll() {
        return perfilDAO.findAll();
    }

}
