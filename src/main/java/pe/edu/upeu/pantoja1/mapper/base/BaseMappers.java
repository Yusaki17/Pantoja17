package pe.edu.upeu.pantoja1.mapper.base;

import java.util.List;

public interface BaseMappers<E, D> {
    D toDTO(E entity);
    E toEntity(D dto);
    List<D> toDTOs(List<E> list);
    List<E> toEntityList(List<D> list);
}
