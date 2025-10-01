package pe.edu.upeu.pantoja1.service.impl;

import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;
import pe.edu.upeu.pantoja1.controller.exceptions.ResourceNotFoundException;
import pe.edu.upeu.pantoja1.dto.NotasDTO;
import pe.edu.upeu.pantoja1.entity.Estudiantes;
import pe.edu.upeu.pantoja1.entity.Materias;
import pe.edu.upeu.pantoja1.entity.Notas;
import pe.edu.upeu.pantoja1.mapper.NotasMapper;
import pe.edu.upeu.pantoja1.repository.EstudiantesRepository;
import pe.edu.upeu.pantoja1.repository.MateriasRepository;
import pe.edu.upeu.pantoja1.repository.NotasRepository;
import pe.edu.upeu.pantoja1.service.service.NotasService;

import java.util.List;
@Service
public class NotasServiceImpl implements NotasService {
    private final NotasRepository notasRepository;
    private final NotasMapper notasMapper;
    private final EstudiantesRepository estudiantesRepository;
    private final MateriasRepository materiasRepository;

    public NotasServiceImpl(NotasRepository notasRepository, NotasMapper notasMapper, EstudiantesRepository estudiantesRepository, MateriasRepository materiasRepository) {
        this.notasRepository = notasRepository;
        this.notasMapper = notasMapper;
        this.estudiantesRepository = estudiantesRepository;
        this.materiasRepository = materiasRepository;
    }

    @Override
    public NotasDTO create(NotasDTO notasDTO) throws ServiceException {
        if(notasDTO==null){
            throw new IllegalArgumentException("La nota no puede ser nulo.");
        }
        try {
            Notas notas = notasMapper.toEntity(notasDTO);
            return notasMapper.toDTO(notasRepository.save(notas));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public NotasDTO update(Long aLong, NotasDTO notasDTO) throws ServiceException {
        if (aLong == null || notasDTO == null) {
            throw new IllegalArgumentException("El ID y las notas no pueden ser nulos");
        }
        Notas existente = notasRepository.findById(aLong)
                .orElseThrow(() -> new ResourceNotFoundException("notas no encontrado con ID: " + aLong));


        try {
            if(notasDTO.getEstudianteId()!=null){
                Estudiantes estudiantes = estudiantesRepository.findById(notasDTO.getEstudianteId())
                        .orElseThrow(()-> new ResourceNotFoundException("estudiantes no encontrado con ID "+aLong));
                existente.setEstudianteId(estudiantes);
            }
            if(notasDTO.getMateriaId()!=null){
                Materias materias = materiasRepository.findById(notasDTO.getMateriaId())
                        .orElseThrow(()-> new ResourceNotFoundException("materias no encontrado con ID "+aLong));
                existente.setMateriaId(materias);

            }

            Notas actualizado = notasRepository.save(existente);
            return notasMapper.toDTO(actualizado);

        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public NotasDTO findById(Long aLong) throws ServiceException {
        if (aLong == null) {
            throw new IllegalArgumentException("El ID no puede ser nulos");
        }
        Notas existente = notasRepository.findById(aLong)
                .orElseThrow(() -> new ResourceNotFoundException("nota no encontrado con ID: " + aLong));
        try {
            return notasMapper.toDTO(existente);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Long aLong) throws ServiceException {
        if (aLong == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }
        Notas existente = notasRepository.findById(aLong)
                .orElseThrow(() -> new ResourceNotFoundException("notas no encontrado con ID: " + aLong));
        try{
            notasRepository.delete(existente);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<NotasDTO> findAll() throws ServiceException {
        List<Notas> notas = notasRepository.findAll();
        if (notas.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron notas registrados");
        }

        return notas.stream()
                .map(notasMapper::toDTO)
                .toList();
    }
}
