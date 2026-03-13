package com.teste.digio.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaiorCompraResponse {

    private String nome;
    private String cpf;
    private String tipoVinho;
    private Integer quantidade;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal valorTotal;

}
