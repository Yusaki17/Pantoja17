package pe.edu.upeu.pantoja1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.edu.upeu.pantoja1.dto.NotasDTO;
import pe.edu.upeu.pantoja1.entity.Notas;
import pe.edu.upeu.pantoja1.mapper.base.BaseMappers;


@Mapper(componentModel = "spring")
public interface NotasMapper extends BaseMappers<Notas, NotasDTO> {

    @Mapping(source = "estudianteId.id", target = "estudianteId")
    @Mapping(source = "materiaId.id", target = "materiaId")
    NotasDTO toDTO(Notas entity);

    @Mapping(source = "estudianteId", target = "estudianteId.id")
    @Mapping(source = "materiaId", target = "materiaId.id")
    Notas toEntity(NotasDTO dto);

}
