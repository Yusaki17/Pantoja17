package pe.edu.upeu.pantoja1.controller.general;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.pantoja1.dto.EstudiantesDTO;
import pe.edu.upeu.pantoja1.service.service.EstudiantesService;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/v1/estudiantes")
public class EstudiantesController {
    private final EstudiantesService estudiantesService;

    public EstudiantesController(EstudiantesService estudiantesService) {

        this.estudiantesService = estudiantesService;
    }
    @GetMapping
    public ResponseEntity<List<EstudiantesDTO>> listAll() throws ServiceException {

        return ResponseEntity.ok(estudiantesService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<EstudiantesDTO> read(@PathVariable Long id) throws ServiceException {
        EstudiantesDTO estudiantesDTO = estudiantesService.findById(id);
        return ResponseEntity.ok(estudiantesDTO);
    }
    @PostMapping
    public ResponseEntity<EstudiantesDTO> create(@RequestBody EstudiantesDTO estudiantesDTO) throws ServiceException {
        EstudiantesDTO estudiantesDTO1 = estudiantesService.create(estudiantesDTO);
        return new ResponseEntity<>(estudiantesDTO1,HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<EstudiantesDTO> update(@PathVariable Long id, @RequestBody EstudiantesDTO estudiantesDTO) throws ServiceException {
        EstudiantesDTO estudiantesDTO1 = estudiantesService.update(id,estudiantesDTO);
        return ResponseEntity.ok(estudiantesDTO1);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws ServiceException {
        estudiantesService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
