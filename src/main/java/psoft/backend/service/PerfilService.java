package psoft.backend.service;

import org.springframework.stereotype.Service;
import psoft.backend.dao.PerfilDAO;
import psoft.backend.exception.perfil.PerfilNotFoundException;
import psoft.backend.exception.user.UserNotFoundException;
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

    public List<Perfil> createPerfilAll(List<Disciplina> disciplina) {
        List<Perfil> perfils = new ArrayList<Perfil>();
        for (Disciplina d : disciplina) {
            Perfil p = new Perfil(d, new ArrayList<Comentario>(), new ArrayList<User>());
            perfils.add(p);
            disciplinaService.findById(d.getId()).setPerfil(p);
        }
        if (perfils.isEmpty()) {
            throw new InternalError("Algo deu errado!!");
        }
        return perfilDAO.saveAll(perfils);
    }

    public Perfil findByDisciplinaId(long id, User user) {
        Disciplina dis = disciplinaService.findById(id);
        if (dis == null) {
            throw new PerfilNotFoundException("Perfil não encontrado!");
        }
        if (user == null) {
            throw new UserNotFoundException("Usuário não encontrado!");
        }
        Perfil perfil = perfilDAO.findById(dis.getPerfil().getId());
        perfil.setUserAtual(user);
        return perfil;
    }

    public Perfil findById(long id, User user) {
        if (user == null) {
            throw new UserNotFoundException("Usuário não encontrado!");
        }
        Perfil perfil = perfilDAO.findById(id);
        if (perfil == null){
            throw new PerfilNotFoundException("Perfil não encontrado!");
        }
        perfil.setUserAtual(user);
        return perfil;
    }

    public List<Perfil> findAll() {
        List perfil = perfilDAO.findAll();
        if (perfil.isEmpty()) {
            throw new PerfilNotFoundException("Não há disciplinas cadastradas");
        }
        return perfil;
    }

    public Perfil curtirPerfil(long perfilId, User user) {
        Perfil perfil = findById(perfilId, user);
        perfil.setCurtidasUser(user);
        return perfilDAO.save(perfil);
    }

}
