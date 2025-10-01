package pe.edu.upeu.pantoja1.service.impl;

import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;
import pe.edu.upeu.pantoja1.controller.exceptions.ResourceNotFoundException;
import pe.edu.upeu.pantoja1.dto.MateriasDTO;
import pe.edu.upeu.pantoja1.entity.Estudiantes;
import pe.edu.upeu.pantoja1.entity.Materias;
import pe.edu.upeu.pantoja1.mapper.MateriasMapper;
import pe.edu.upeu.pantoja1.repository.MateriasRepository;
import pe.edu.upeu.pantoja1.service.service.MateriasService;

import java.util.List;
@Service
public class MateriasServiceImpl implements MateriasService {
    private MateriasRepository materiasRepository;
    private MateriasMapper materiasMapper;

    public MateriasServiceImpl(MateriasMapper materiasMapper, MateriasRepository materiasRepository) {
        this.materiasMapper = materiasMapper;
        this.materiasRepository = materiasRepository;
    }
    @Override
    public MateriasDTO create(MateriasDTO materiasDTO) throws ServiceException {
        try {
            Materias materias = materiasMapper.toEntity(materiasDTO);
            Materias materiasSaved = materiasRepository.save(materias);
            return materiasMapper.toDTO(materiasSaved);
        } catch (Exception e) {
            throw new ServiceException("Error al crear materias", e);
        }
    }

    @Override
    public MateriasDTO update(Long aLong, MateriasDTO materiasDTO) throws ServiceException {
        try {
            Materias materias = materiasRepository.findById(aLong).orElseThrow(() -> new ResourceNotFoundException("materias no encontrada"));
            materias.setMateria(materiasDTO.getMateria());
            Materias materiasUpdate = materiasRepository.save(materias);
            return materiasMapper.toDTO(materiasUpdate);
        } catch (ResourceNotFoundException e) {
            throw (e);
        }catch (Exception e) {
            throw new ServiceException("Error al actualizar materias",e);
        }
    }

    @Override
    public MateriasDTO findById(Long aLong) throws ServiceException {
        try {
            Materias materias = materiasRepository.findById(aLong).orElseThrow(() -> new ResourceNotFoundException("materia con ID "+aLong+" no fue encontrada"));
            return materiasMapper.toDTO(materias);
        } catch (ResourceNotFoundException e) {
            throw (e);
        } catch (Exception e) {
            throw new ServiceException("Error al leer el materia con id " + aLong, e);
        }
    }

    @Override
    public void deleteById(Long aLong) throws ServiceException {
        try {
            if(!materiasRepository.findById(aLong).isPresent()){
                throw new ResourceNotFoundException("materia con ID "+aLong+" no fue encontrada");
            }
            materiasRepository.deleteById(aLong);
        }catch (ResourceNotFoundException e) {
            throw (e);
        }catch (Exception e) {
            throw new ServiceException("Error al eliminar  materia con id " + aLong, e);
        }
    }

    @Override
    public List<MateriasDTO> findAll() throws ServiceException {
        try {
            List<Materias> materias = materiasRepository.findAll();
            return materiasMapper.toDTOs(materias);
        }catch (Exception e) {
            throw new ServiceException("Error al listar los materias",e);
        }
    }
}
