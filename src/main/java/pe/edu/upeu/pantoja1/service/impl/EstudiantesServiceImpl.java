package pe.edu.upeu.pantoja1.service.impl;

import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;
import pe.edu.upeu.pantoja1.controller.exceptions.ResourceNotFoundException;
import pe.edu.upeu.pantoja1.dto.EstudiantesDTO;
import pe.edu.upeu.pantoja1.entity.Estudiantes;
import pe.edu.upeu.pantoja1.mapper.EstudiantesMapper;
import pe.edu.upeu.pantoja1.repository.EstudiantesRepository;
import pe.edu.upeu.pantoja1.service.service.EstudiantesService;

import java.util.List;
@Service
public class EstudiantesServiceImpl implements EstudiantesService {

    private final EstudiantesMapper estudiantesMapper;
    private final EstudiantesRepository estudiantesRepository;

    public EstudiantesServiceImpl( EstudiantesMapper estudiantesMapper, EstudiantesRepository estudiantesRepository) {
        this.estudiantesMapper = estudiantesMapper;
        this.estudiantesRepository = estudiantesRepository;
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
        try {
            Estudiantes estudiantes = estudiantesRepository.findById(aLong).orElseThrow(() -> new ResourceNotFoundException("estudiantes no encontrada"));
            estudiantes.setNombre(estudiantesDTO.getNombre());
            Estudiantes estudiantesUpdate = estudiantesRepository.save(estudiantes);
            return estudiantesMapper.toDTO(estudiantesUpdate);
        } catch (ResourceNotFoundException e) {
            throw (e);
        }catch (Exception e) {
            throw new ServiceException("Error al actualizar estudiantes",e);
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
