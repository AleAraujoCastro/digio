package com.teste.digio.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ComprasClienteResponse {

    private String nomeCliente;
    private String cpfCliente;
    private Integer quantidadeCompras;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal valorTotalGasto;

    private List<ProdutoResponse> produtosCompras;

}
