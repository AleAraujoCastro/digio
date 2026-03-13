package com.teste.digio.client;

import com.teste.digio.dto.ProdutoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "produto", url = "${api.produtos}")
public interface ProdutosClient {

    @RequestMapping(method = RequestMethod.GET)
    List<ProdutoDTO> buscarProdutos();

}