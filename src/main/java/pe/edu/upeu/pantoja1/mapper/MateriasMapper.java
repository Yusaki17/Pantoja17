package pe.edu.upeu.pantoja1.mapper;

import org.mapstruct.Mapper;
import pe.edu.upeu.pantoja1.dto.MateriasDTO;
import pe.edu.upeu.pantoja1.entity.Materias;
import pe.edu.upeu.pantoja1.mapper.base.BaseMappers;
@Mapper(componentModel = "spring")
public interface MateriasMapper extends BaseMappers<Materias, MateriasDTO> {
}
