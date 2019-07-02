package psoft.backend.service;

import org.springframework.stereotype.Service;
import psoft.backend.dao.ComentarioDAO;
import psoft.backend.exception.comentario.ComentarioDeleteException;
import psoft.backend.exception.comentario.ComentarioInvalidoException;
import psoft.backend.exception.comentario.ComentarioNotFoundException;
import psoft.backend.exception.comentario.ComentarioNullException;
import psoft.backend.exception.perfil.PerfilNotFoundException;
import psoft.backend.model.Comentario;
import psoft.backend.model.Perfil;
import psoft.backend.model.User;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ComentarioService {

    private final ComentarioDAO comentarioDAO;

    public ComentarioService(ComentarioDAO comentarioDAO) {
        this.comentarioDAO = comentarioDAO;
    }

    public Comentario create(Perfil perfil, User user, String text, long comentarioId) {
        if (text == null) throw new ComentarioNullException("O comentário não pode ser Null");
        if (text.trim().equals(""))
            throw new ComentarioInvalidoException("O comentário não pode ser vazio, insira um comentário valido");
        if (user == null) throw new ComentarioNullException("O usuário não pode ser Null");
        if (perfil == null) throw new ComentarioNullException("O perfil não pode ser Null");

        String hora = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).format(DateTimeFormatter.ofPattern("HH:mm"));
        String data = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Comentario comentario = new Comentario(perfil, user, text, hora, data, new ArrayList<Comentario>());
        if (comentarioId == 0) {
            perfil.addComentario(comentario);
        } else {
            Comentario comentarioPai = findById(comentarioId);
            comentarioPai.addResposta(comentario);
        }

        return comentarioDAO.save(comentario);
    }

    public Comentario findById(long id) {
        Comentario comentario = comentarioDAO.findById(id);
        if (comentario == null) {
            throw new ComentarioNullException("Comentário não existe!");
        }
        if (comentario.isApagado()) {
            throw new ComentarioDeleteException("Comentário apagado!");
        }
        return comentario;
    }

    public List<Comentario> findAll() {
        List<Comentario> comentarios = comentarioDAO.findAll();
        if (comentarios.isEmpty()) {
            throw new PerfilNotFoundException("Não há disciplinas cadastradas");
        }
        return comentarios;
    }

    public Comentario deleteUpdate(long comentarioId, String userMail) {
        Comentario comentario = comentarioDAO.findById(comentarioId);
        if (comentario == null) {
            throw new ComentarioNotFoundException("Comentário não encontrado!!");
        }
        if (!(comentario.getUser().getEmail().equals(userMail))) {
            throw new ComentarioDeleteException("Esse comentário não pertece ao usuário passado no token");
        }
        comentario.setApagado(true);
        return comentarioDAO.save(comentario);
    }
}
