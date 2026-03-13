package com.teste.digio.controller;

import com.teste.digio.dto.ProdutoDTO;
import com.teste.digio.dto.response.ClienteFielResponse;
import com.teste.digio.dto.response.ComprasClienteResponse;
import com.teste.digio.dto.response.MaiorCompraResponse;
import com.teste.digio.service.LojaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loja")
public class LojaController {

    @Autowired
    private LojaService lojaService;

    @GetMapping("/compras")
    public ResponseEntity<Page<ComprasClienteResponse>> compras(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "40") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ComprasClienteResponse> clientes =lojaService.listarComprasPageada(pageable);

        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/maior-compra/{ano}")
    public MaiorCompraResponse maiorCompra(@PathVariable Integer ano) {
        return lojaService.maiorCompraPorAno(ano);
    }

    @GetMapping("/clientes-fieis")
    public List<ClienteFielResponse> clientesFieis() {
        return lojaService.clientesFieis();
    }

    @GetMapping("/recomendacao/{cpf}")
    public ProdutoDTO recomendacao(@PathVariable String cpf) {
        return lojaService.recomendacao(cpf);
    }
}