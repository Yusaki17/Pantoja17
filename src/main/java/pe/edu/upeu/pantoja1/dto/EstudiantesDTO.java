package pe.edu.upeu.pantoja1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EstudiantesDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;

    private List<NotasDTO> notas;
}

