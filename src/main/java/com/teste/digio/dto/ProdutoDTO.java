package com.teste.digio.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProdutoDTO {

    private Integer codigo;

    @JsonProperty("tipo_vinho")
    private String tipoVinho;

    private Float preco;

    private String safra;

    @JsonProperty("ano_compra")
    private int anoCompra;

}
