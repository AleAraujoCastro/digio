package dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LojaResponse {

    private String nomeCliente;
    private String cpf;
    private String tipoVinho;
    private Integer quantidade;
    private Double valorTotal;
}
