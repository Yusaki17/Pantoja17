package pe.edu.upeu.pantoja1.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotasDTO {
    private Long id;
    private Long materiaId;
    private Long estudianteId;
    private String nota;
}
