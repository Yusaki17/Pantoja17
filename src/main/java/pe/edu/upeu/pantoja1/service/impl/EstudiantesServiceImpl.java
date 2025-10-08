package pe.edu.upeu.pantoja1.service.impl;

import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;
import pe.edu.upeu.pantoja1.controller.exceptions.ResourceNotFoundException;
import pe.edu.upeu.pantoja1.dto.EstudiantesDTO;
import pe.edu.upeu.pantoja1.dto.NotasDTO;
import pe.edu.upeu.pantoja1.entity.Estudiantes;
import pe.edu.upeu.pantoja1.entity.Materias;
import pe.edu.upeu.pantoja1.entity.Notas;
import pe.edu.upeu.pantoja1.mapper.EstudiantesMapper;
import pe.edu.upeu.pantoja1.repository.EstudiantesRepository;
import pe.edu.upeu.pantoja1.repository.MateriasRepository;
import pe.edu.upeu.pantoja1.repository.NotasRepository;
import pe.edu.upeu.pantoja1.service.service.EstudiantesService;

import java.util.List;
@Service
public class EstudiantesServiceImpl implements EstudiantesService {

    private final EstudiantesMapper estudiantesMapper;
    private final EstudiantesRepository estudiantesRepository;
    private final NotasRepository notasRepository;
    private final MateriasRepository materiasRepository;

    public EstudiantesServiceImpl(EstudiantesMapper estudiantesMapper, EstudiantesRepository estudiantesRepository, NotasRepository notasRepository, MateriasRepository materiasRepository) {
        this.estudiantesMapper = estudiantesMapper;
        this.estudiantesRepository = estudiantesRepository;
        this.notasRepository = notasRepository;
        this.materiasRepository = materiasRepository;
    }


    @Override
    public EstudiantesDTO create(EstudiantesDTO estudiantesDTO) throws ServiceException {
        try {
            Estudiantes estudiantes = estudiantesMapper.toEntity(estudiantesDTO);
            Estudiantes estudiantesSaved = estudiantesRepository.save(estudiantes);
            return estudiantesMapper.toDTO(estudiantesSaved);
        } catch (Exception e) {
            throw new ServiceException("Error al crear estudiantes", e);
        }
    }
    @Override
    public EstudiantesDTO update(Long aLong, EstudiantesDTO estudiantesDTO) throws ServiceException {
        if (aLong == null || estudiantesDTO == null) {
            throw new IllegalArgumentException("El ID y el estudiante no pueden ser nulos");
        }

        Estudiantes existente = estudiantesRepository.findById(aLong)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con ID: " + aLong));

        try {
            // Actualizar campos básicos del estudiante
            existente.setNombre(estudiantesDTO.getNombre());
            existente.setApellido(estudiantesDTO.getApellido());
            existente.setDireccion(estudiantesDTO.getDireccion());
            existente.setTelefono(estudiantesDTO.getTelefono());

            // Manejo de la lista notas
            if(estudiantesDTO.getNotas() != null && !estudiantesDTO.getNotas().isEmpty()) {
                // Limpiar la lista existente
                existente.getNotas().clear();

                // Agregar las nuevas notas
                for(NotasDTO notaDTO : estudiantesDTO.getNotas()) {
                    Notas nota;

                    // Si tiene ID, buscar la existente; si no, crear nueva
                    if(notaDTO.getId() != null) {
                        nota = notasRepository.findById(notaDTO.getId())
                                .orElse(new Notas());
                    } else {
                        nota = new Notas();
                    }

                    // Establecer la nota (el valor)
                    nota.setNota(notaDTO.getNota());

                    // Establecer el estudiante (siempre el que estamos editando)
                    nota.setEstudianteId(existente);

                    // Establecer la materia
                    if(notaDTO.getMateriaId() != null) {
                        Materias materias = materiasRepository.findById(notaDTO.getMateriaId())
                                .orElseThrow(() -> new ResourceNotFoundException("Materia no encontrada con ID " + notaDTO.getMateriaId()));
                        nota.setMateriaId(materias);
                    }

                    // Agregar la nota a la lista del estudiante
                    existente.getNotas().add(nota);
                }
            }

            // Guardar el estudiante con sus notas
            Estudiantes actualizado = estudiantesRepository.save(existente);
            return estudiantesMapper.toDTO(actualizado);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public EstudiantesDTO findById(Long aLong) throws ServiceException {
        try {
            Estudiantes estudiantes = estudiantesRepository.findById(aLong).orElseThrow(() -> new ResourceNotFoundException("estudiantes con ID "+aLong+" no fue encontrada"));
            return estudiantesMapper.toDTO(estudiantes);
        } catch (ResourceNotFoundException e) {
            throw (e);
        } catch (Exception e) {
            throw new ServiceException("Error al leer el estudiante con id " + aLong, e);
        }
    }

    @Override
    public void deleteById(Long aLong) throws ServiceException {
        try {
            if(!estudiantesRepository.findById(aLong).isPresent()){
                throw new ResourceNotFoundException("estudiante con ID "+aLong+" no fue encontrada");
            }
            estudiantesRepository.deleteById(aLong);
        }catch (ResourceNotFoundException e) {
            throw (e);
        }catch (Exception e) {
            throw new ServiceException("Error al eliminar el estudiantes con id " + aLong, e);
        }
    }

    @Override
    public List<EstudiantesDTO> findAll() throws ServiceException {
        try {
            List<Estudiantes> estudiantes = estudiantesRepository.findAll();
            return estudiantesMapper.toDTOs(estudiantes);
        }catch (Exception e) {
            throw new ServiceException("Error al listar los Estudiantes",e);
        }
    }
}
