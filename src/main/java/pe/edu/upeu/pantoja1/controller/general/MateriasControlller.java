package pe.edu.upeu.pantoja1.controller.general;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.pantoja1.dto.MateriasDTO;
import pe.edu.upeu.pantoja1.service.service.MateriasService;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/v1/materias")
public class MateriasControlller {
    private final MateriasService materiasService;

    public MateriasControlller (MateriasService materiasService) {

        this.materiasService = materiasService;
    }

    @GetMapping
    public ResponseEntity<List<MateriasDTO>> listAll() throws ServiceException {

        return ResponseEntity.ok(materiasService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<MateriasDTO> read(@PathVariable Long id) throws ServiceException {
        MateriasDTO materiasDTO = materiasService.findById(id);
        return ResponseEntity.ok(materiasDTO);
    }
    @PostMapping
    public ResponseEntity<MateriasDTO> create(@RequestBody MateriasDTO materiasDTO) throws ServiceException {
        MateriasDTO materiasDTO1 = materiasService.create(materiasDTO);
        return new ResponseEntity<>(materiasDTO1, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<MateriasDTO> update(@PathVariable Long id, @RequestBody MateriasDTO materiasDTO) throws ServiceException {
        MateriasDTO materiasDTO1 = materiasService.update(id,materiasDTO);
        return ResponseEntity.ok(materiasDTO1);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws ServiceException {
        materiasService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
