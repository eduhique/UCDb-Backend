package psoft.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import psoft.backend.model.Disciplina;

import java.io.Serializable;
import java.util.List;


@Repository
public interface DisciplinaDAO<T, ID extends Serializable> extends JpaRepository<Disciplina, Long> {

    List<Disciplina> saveAll(Iterable disciplinaList);

    List<Disciplina> findAll();

    Disciplina findById(long id);
}
