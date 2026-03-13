package com.teste.digio.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class    ClienteFielResponse {

    private String nome;
    private String cpf;
    private Integer totalCompras;
    private BigDecimal valorTotalGasto;
}
