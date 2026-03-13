package com.teste.digio.service;

import com.teste.digio.dto.response.ClienteFielResponse;
import com.teste.digio.dto.response.ComprasClienteResponse;
import com.teste.digio.dto.response.MaiorCompraResponse;
import com.teste.digio.dto.ProdutoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LojaService {

    Page<ComprasClienteResponse> listarComprasPageada(Pageable pageable);

    List<ComprasClienteResponse> listarCompras();

    MaiorCompraResponse maiorCompraPorAno(Integer ano);

    List<ClienteFielResponse> clientesFieis();

    ProdutoDTO recomendacao(String cpf);

}