package psoft.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import psoft.backend.model.Perfil;

import java.io.Serializable;
import java.util.List;

@Repository
public interface PerfilDAO<T, ID extends Serializable> extends JpaRepository<Perfil, Long> {

    Perfil save(Perfil s);

    List<Perfil> saveAll(Iterable disciplinaList);

    List<Perfil> findAll();

    Perfil findById(long id);
}
