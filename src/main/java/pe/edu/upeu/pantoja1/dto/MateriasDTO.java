package pe.edu.upeu.pantoja1.dto;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MateriasDTO {
    private Long id;
    private String materia;

}
