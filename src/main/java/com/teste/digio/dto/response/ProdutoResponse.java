package com.teste.digio.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.teste.digio.dto.ProdutoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoResponse {

    private ProdutoDTO produtoDto;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal valorTotalCompra;

    private int quantidade;
}
