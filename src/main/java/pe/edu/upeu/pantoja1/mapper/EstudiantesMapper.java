package pe.edu.upeu.pantoja1.mapper;

import org.mapstruct.Mapper;
import pe.edu.upeu.pantoja1.dto.EstudiantesDTO;
import pe.edu.upeu.pantoja1.entity.Estudiantes;
import pe.edu.upeu.pantoja1.mapper.base.BaseMappers;
@Mapper(componentModel = "spring", uses = {NotasMapper.class})
public interface EstudiantesMapper extends BaseMappers<Estudiantes, EstudiantesDTO> {
}
