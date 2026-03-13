package com.teste.digio.service.impl;

import com.teste.digio.client.ClientesComprasClient;
import com.teste.digio.client.ProdutosClient;
import com.teste.digio.dto.ClienteDTO;
import com.teste.digio.dto.CompraDTO;
import com.teste.digio.dto.ProdutoDTO;
import com.teste.digio.dto.response.ClienteFielResponse;
import com.teste.digio.dto.response.ComprasClienteResponse;
import com.teste.digio.dto.response.MaiorCompraResponse;
import com.teste.digio.dto.response.ProdutoResponse;
import com.teste.digio.service.LojaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LojaServiceImpl implements LojaService {

    @Autowired
    private ProdutosClient produtosClient;

    @Autowired
    private ClientesComprasClient clientesComprasClient;

    @Override
    public Page<ComprasClienteResponse> listarComprasPageada(Pageable pageable) {

        List<ComprasClienteResponse> lista = listarCompras();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), lista.size());

        List<ComprasClienteResponse> pageContent =
                start >= end ? List.of() : lista.subList(start, end);

        return new PageImpl<>(pageContent, pageable, lista.size());
    }

    @Override
    public List<ComprasClienteResponse> listarCompras() {

        Map<Integer, ProdutoDTO> produtos =
                produtosClient.buscarProdutos()
                        .stream()
                        .collect(Collectors.toMap(ProdutoDTO::getCodigo, p -> p));

        List<ClienteDTO> clientes = clientesComprasClient.buscarClientesECompras();

        List<ComprasClienteResponse> resultado = new ArrayList<>();

        for (ClienteDTO cliente : clientes) {

            List<ProdutoResponse> produtosComprados = new ArrayList<>();

            int quantidadeTotal = 0;
            BigDecimal valorTotal = BigDecimal.ZERO;

            for (CompraDTO compra : cliente.getCompras()) {

                ProdutoDTO produto = produtos.get(compra.getCodigo());

                BigDecimal valorCompra =
                        BigDecimal.valueOf(produto.getPreco())
                                .multiply(BigDecimal.valueOf(compra.getQuantidade()))
                                .setScale(2, RoundingMode.HALF_UP);

                quantidadeTotal += compra.getQuantidade();
                valorTotal = valorTotal.add(valorCompra);

                ProdutoResponse produtoResponse = new ProdutoResponse(
                        produto,
                        valorCompra,
                        compra.getQuantidade()
                );

                produtosComprados.add(produtoResponse);
            }

            ComprasClienteResponse response = new ComprasClienteResponse(
                    cliente.getNome(),
                    cliente.getCpf(),
                    quantidadeTotal,
                    valorTotal,
                    produtosComprados
            );

            resultado.add(response);
        }

        return resultado;
    }

    @Override
    public MaiorCompraResponse maiorCompraPorAno(Integer ano) {

        List<ComprasClienteResponse> compras = listarCompras();

        MaiorCompraResponse maior = null;

        for (ComprasClienteResponse cliente : compras) {

            for (ProdutoResponse produto : cliente.getProdutosCompras()) {

                ProdutoDTO p = produto.getProdutoDto();

                if (p.getAnoCompra() == ano) {

                    BigDecimal valor = produto.getValorTotalCompra()
                            .setScale(2, RoundingMode.HALF_UP);

                    if (maior == null ||
                            valor.compareTo(maior.getValorTotal()) > 0) {

                        maior = MaiorCompraResponse.builder()
                                .nome(cliente.getNomeCliente())
                                .cpf(cliente.getCpfCliente())
                                .tipoVinho(p.getTipoVinho())
                                .valorTotal(valor)
                                .build();
                    }
                }
            }
        }

        return maior;
    }

    @Override
    public List<ClienteFielResponse> clientesFieis() {

        List<ComprasClienteResponse> compras = listarCompras();

        return compras.stream()
                .map(c -> ClienteFielResponse.builder()
                        .nome(c.getNomeCliente())
                        .cpf(c.getCpfCliente())
                        .totalCompras(c.getQuantidadeCompras())
                        .valorTotalGasto((BigDecimal) c.getValorTotalGasto())
                        .build())
                .sorted(Comparator.comparing(ClienteFielResponse::getValorTotalGasto).reversed())
                .limit(3)
                .toList();
    }

    @Override
    public ProdutoDTO recomendacao(String cpf) {

        List<ComprasClienteResponse> compras = listarCompras();

        Optional<ComprasClienteResponse> cliente =
                compras.stream()
                        .filter(c -> c.getCpfCliente().equals(cpf))
                        .findFirst();

        if (cliente.isEmpty()) {
            return null;
        }

        Map<String, Integer> tipos =
                cliente.get().getProdutosCompras()
                        .stream()
                        .collect(Collectors.groupingBy(
                                p -> p.getProdutoDto().getTipoVinho(),
                                Collectors.summingInt(ProdutoResponse::getQuantidade)
                        ));

        String favorito =
                tipos.entrySet()
                        .stream()
                        .max(Map.Entry.comparingByValue())
                        .map(Map.Entry::getKey)
                        .orElse(null);

        return produtosClient.buscarProdutos()
                .stream()
                .filter(p -> p.getTipoVinho().equals(favorito))
                .findFirst()
                .orElse(null);
    }
}