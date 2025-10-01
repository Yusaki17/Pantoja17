package pe.edu.upeu.pantoja1.controller.general;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.pantoja1.dto.NotasDTO;
import pe.edu.upeu.pantoja1.service.service.NotasService;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/v1/notas")
public class NotasController {
    private final NotasService notasService;

    public NotasController(NotasService notasService) {
        this.notasService = notasService;
    }

    @GetMapping
    public ResponseEntity<List<NotasDTO>> listAll() throws ServiceException {

        return ResponseEntity.ok(notasService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<NotasDTO> read(@PathVariable Long id) throws ServiceException {
        NotasDTO notasDTO = notasService.findById(id);
        return ResponseEntity.ok(notasDTO);
    }
    @PostMapping
    public ResponseEntity<NotasDTO> create(@RequestBody NotasDTO notasDTO) throws ServiceException {
        NotasDTO notasDTO1 = notasService.create(notasDTO);
        return new ResponseEntity<>(notasDTO1, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<NotasDTO> update(@PathVariable Long id, @RequestBody NotasDTO notasDTO) throws ServiceException {
        NotasDTO notasDTO1 = notasService.update(id,notasDTO);
        return ResponseEntity.ok(notasDTO1);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws ServiceException {
        notasService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
