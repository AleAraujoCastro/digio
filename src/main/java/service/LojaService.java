package service;

import client.LojaClient;
import dto.ClienteDTO;
import dto.CompraDTO;
import dto.ProdutoDTO;
import dto.response.LojaResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LojaService {

    @Autowired
    private LojaClient lojaClient;

    private List<ProdutoDTO> produtos;
    private List<ClienteDTO> clientes;

    @PostConstruct
    public void carregarDados() {
        produtos = lojaClient.buscarProdutos();
        clientes = lojaClient.buscarClientes();
    }

    private ProdutoDTO buscarProduto(Integer codigo){
        return produtos.stream()
                .filter(p -> p.getCodigo().equals(codigo))
                .findFirst()
                .orElseThrow();
    }

    public List<LojaResponse> listarCompras(){

        List<LojaResponse> lista = new ArrayList<>();

        for(ClienteDTO cliente : clientes){

            for(CompraDTO compra : cliente.getCompras()){

                ProdutoDTO produto = buscarProduto(compra.getCodigo());

                Double valorTotal = produto.getPreco() * compra.getQuantidade();

                lista.add(new LojaResponse(
                        cliente.getNome(),
                        cliente.getCpf(),
                        produto.getTipo_vinho(),
                        compra.getQuantidade(),
                        valorTotal
                ));
            }
        }

        return lista.stream()
                .sorted(Comparator.comparing(LojaResponse::getValorTotal))
                .toList();
    }

    public LojaResponse maiorCompraAno(Integer ano){

        return listarCompras().stream()
                .max(Comparator.comparing(LojaResponse::getValorTotal))
                .orElseThrow();
    }

    public List<String> clientesFieis(){

        Map<String, Double> totalPorCliente = new HashMap<>();

        for(ClienteDTO cliente : clientes){

            double total = cliente.getCompras().stream()
                    .mapToDouble(c -> {
                        ProdutoDTO produto = buscarProduto(c.getCodigo());
                        return produto.getPreco() * c.getQuantidade();
                    })
                    .sum();

            totalPorCliente.put(cliente.getCpf(), total);
        }

        return totalPorCliente.entrySet()
                .stream()
                .sorted(Map.Entry.<String,Double>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .toList();
    }

    public String recomendacaoCliente(String cpf){

        ClienteDTO cliente = clientes.stream()
                .filter(c -> c.getCpf().equals(cpf))
                .findFirst()
                .orElseThrow();

        Map<String, Integer> tipos = new HashMap<>();

        for(CompraDTO compra : cliente.getCompras()){

            ProdutoDTO produto = buscarProduto(compra.getCodigo());

            tipos.put(
                    produto.getTipo_vinho(),
                    tipos.getOrDefault(produto.getTipo_vinho(),0) + compra.getQuantidade()
            );
        }

        return tipos.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();
    }


}
