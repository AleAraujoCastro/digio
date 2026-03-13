package com.teste.digio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompraDTO {

    private Integer codigo;
    private Integer quantidade;
    private String data;

}
