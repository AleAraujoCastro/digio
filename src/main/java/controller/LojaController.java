package controller;

import dto.ProdutoDTO;
import dto.response.LojaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.LojaService;

import java.util.List;

@RestController
@RequestMapping("/loja")
@RequiredArgsConstructor
public class LojaController {

    @Autowired
    private LojaService lojaService;

    @GetMapping("/compras")
    public List<LojaResponse> compras(){
        return lojaService.listarCompras();
    }

    @GetMapping("/maior-compra/ano/{ano}")
    public LojaResponse maiorCompra(@PathVariable Integer ano){
        return lojaService.maiorCompraAno(ano);
    }

    @GetMapping("/clientes-fieis")
    public List<String> clientesFieis(){
        return lojaService.clientesFieis();
    }

    @GetMapping("/recomendacao/cliente/{cpf}")
    public String recomendacao(@PathVariable String cpf){
        return lojaService.recomendacaoCliente(cpf);
    }

}
