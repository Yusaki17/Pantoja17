package pe.edu.upeu.pantoja1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.pantoja1.entity.Materias;
import pe.edu.upeu.pantoja1.mapper.base.BaseMappers;

public interface MateriasRepository extends JpaRepository<Materias, Long> {
}
