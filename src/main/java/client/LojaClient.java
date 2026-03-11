package client;

import dto.ClienteDTO;
import dto.ProdutoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "lojaClient", url = "${api.loja.url}")
public interface LojaClient {

    @GetMapping("/produtos-mnboX5IPI6VgG390FECTKqHsD9SkLS.json")
    List<ProdutoDTO> buscarProdutos();

    @GetMapping("/clientes-Vz1U6aR3GTsjb3WB8BRJhcNKmA81pVh.json")
    List<ClienteDTO> buscarClientes();


}
